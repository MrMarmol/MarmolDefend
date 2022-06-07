package frames.partida;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.tablero.Tablero;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.concurrent.Task;
import procesos.acciones.AccionesDTO.Accion;
import procesos.acciones.AccionesDTO.AccionAlojar;
import procesos.acciones.AccionesDTO.AccionAndar;
import procesos.acciones.AccionesDTO.AccionAtacar;
import procesos.acciones.AccionesDTO.AccionConstruir;
import procesos.acciones.AccionesDTO.AccionCorrer;
import procesos.acciones.AccionesDTO.AccionDesalojar;
import procesos.acciones.AccionesDTO.AccionHabilidad;
import procesos.acciones.AccionesDTO.AccionHabilidadArea;
import procesos.acciones.AccionesDTO.AccionHabilidadDiana;
import procesos.acciones.AccionesDTO.AccionHabilidadLineal;
import procesos.acciones.AccionesDTO.AccionProducir;
import procesos.habilidades.Habilidad;
import procesos.habilidades.HabilidadArea;
import procesos.habilidades.HabilidadDiana;
import procesos.habilidades.HabilidadLineal;

public class PrototipoComunicador {

	private final String CLIENTE = "CLIENTE";
	private final String HOST = "HOST";
	private String rol;

	private Task<Void> tareaHostEscucha;
	private Task<Void> tareaClienteEnvia;
	private Task<Void> tareaClienteEscucha;
	private Task<Void> tareaEscuchaAccion;
	private boolean jugador_conectado;
	private Accion accion;
	
	private ServerSocket server;
	private Socket conexion;
	private ControladorPartida cp;
	private String ip;
	private int puerto;
	private volatile boolean miTurno;

	public boolean miTurno() {
		return miTurno;
	}
	public PrototipoComunicador(int puerto, String grupo,String rol) {
		this.puerto = puerto;
		this.rol = rol;

		if(rol.equals(HOST))
		tareaHostEscucha = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				MulticastSocket recepcion = new MulticastSocket(puerto);
				InetAddress grupo = InetAddress.getByName ("225.0.0.1");
				InetAddress ia= InetAddress.getLocalHost();
				String ipHost = ia.getHostAddress();
				recepcion.joinGroup(grupo);

				byte[] buffer = new byte[1024];
				DatagramPacket paqueteCliente = new DatagramPacket(buffer,buffer.length);
				String ipCliente;
				Socket socket;
				DataOutputStream paqueteHost;
				
				while(!jugador_conectado) {
					try {
						recepcion.receive(paqueteCliente);
						ipCliente = new String(paqueteCliente.getData());
						socket = new Socket(ipCliente,puerto);
						ip = ipCliente;
						paqueteHost = new DataOutputStream(socket.getOutputStream()); 
						paqueteHost.writeUTF(ipHost);
						jugador_conectado = true;
					}
					catch(IOException cliente_incomunicado) {}
				}
				return null;

			}};
			else if(rol.equals(CLIENTE)) {
			tareaClienteEnvia = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					
					InetAddress grupo = InetAddress.getByName ("225.0.0.1") ;
					InetAddress ia= InetAddress.getLocalHost();
					String ip = ia.getHostAddress();
					MulticastSocket envio;
					DatagramPacket paquete;

					//Cada tres segundos envía su ip
					while(!jugador_conectado) {
						envio = new MulticastSocket();
						paquete = new DatagramPacket(ip.getBytes(), ip.length(), grupo, puerto);																
						envio.send (paquete);								
						envio.close() ;
						Thread.sleep(3000);
					}
					return null;
				}};				
				 tareaClienteEscucha = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						ServerSocket escucha = new ServerSocket(puerto);
						Socket host = server.accept();
						DataInputStream ipHost = new DataInputStream(host.getInputStream());
						ip = ipHost.readUTF();
						jugador_conectado = true;
						return null;
					}
					
				};
			}
				//escuchaPartida
			tareaEscuchaAccion = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					while(true) {
						try {
							System.out.println("Esperando accion");
							conexion = server.accept();
							System.out.print("paquete recibido : ");
							ObjectInputStream paquete_recibo = new ObjectInputStream(conexion.getInputStream());
							accion = ((Accion) paquete_recibo.readObject());
							System.out.println(accion.getAccion());
							ejecutar_accion(accion);
							paquete_recibo.close();
							conexion.close();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}					
				}};
	}
	
	private boolean buscar_partida() {
		if(rol.equals(HOST)) {
			Thread hilo = new Thread(tareaHostEscucha);
			hilo.setDaemon(true);
			hilo.start();
		}
		else {
			Thread hilo = new Thread(tareaClienteEnvia);
			hilo.setDaemon(true);
			hilo.start();
			Thread hilo2 = new Thread(tareaClienteEscucha);
			hilo2.setDaemon(true);
			hilo2.start();
		}
		while(!jugador_conectado)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		return true;

			
	}
	

	//Método llamado por el hilo fx
	public void iniciar_partida() throws IOException {
		if(rol.equals(HOST))
		{
			server = new ServerSocket(puerto);
			miTurno = true;
			cargar_partida();
			Thread hilo = new Thread(tareaEscuchaAccion);
			hilo.setDaemon(true);
			hilo.start();
		}
		if(rol.equals(CLIENTE))
		{
			server = new ServerSocket(puerto);
			cargar_partida();
			cp.turno = false;
	    	cp.tabla_inferior.setVisible(false);    	
			Thread hilo = new Thread(tareaEscuchaAccion);
			hilo.setDaemon(true);
			hilo.start();
		}
	}	

	/*Para evitar que otros hilos interaccionen con la interfaz del hijo fx, estos solo recibirán la accion y el hilo fx se encargará de ejecutarla.*/
	//No puede acceder a la variable miTurno porque esta siendo usado por el servidor en bucle al igual que la variable accion, estos se sustituyen por escuchaFX y accionRecibida


	public void terminar_turno() {
		System.out.println("terminar turno del comunicador");
		miTurno = false;
		Accion accion = new Accion(null,DatosGlobales.ACCION_TURNO_TERMINADO);
		enviar_accion(accion);
	//	Thread hilo = new Thread(tareaEscuchaAccion);
	//	hilo.start();
	

	}
	public void enviar_accion(Accion accion) {
		try {
			if(rol.equals(HOST))
			conexion = new Socket(ip,puerto);
			else {
				conexion = new Socket(ip,puerto);
				System.out.println("Enviando accion al host : "+accion.getAccion());
				}
			ObjectOutputStream paquete_envio;
			paquete_envio = new ObjectOutputStream (conexion.getOutputStream());
			paquete_envio.writeObject(accion);
			paquete_envio.close();
			conexion.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void ejecutar_accion(Accion accion) {

		Casilla casillaActual;
		Casilla casillaSeleccionada;
		Tablero tablero = cp.obtener_tablero();
		
		if(accion instanceof AccionAtacar) {
			AccionAtacar accionAtacar = (AccionAtacar) accion;	
			casillaActual = tablero.get_casilla(accionAtacar.getCasillaActualX(), accionAtacar.getCasillaActualY());
			casillaSeleccionada = tablero.get_casilla(accionAtacar.getCasillaSeleccionadaX(), accionAtacar.getCasillaSeleccionadaY());
			cp.atacar(casillaActual,casillaSeleccionada);
		}
		else if(accion instanceof AccionConstruir) {			
			AccionConstruir accionConstruir = (AccionConstruir) accion;		
			casillaActual = tablero.get_casilla(accionConstruir.getCasillaActualX(), accionConstruir.getCasillaActualY());
			for(Edificio edificio : DatosGlobales.edificios)
				if(edificio.getNombre().equals(accionConstruir.getNombreEdificio())) {
						cp.construir(edificio,casillaActual);
						break;
				}
		}
		else if(accion instanceof AccionProducir) {
			AccionProducir accionProducir = (AccionProducir) accion;
			casillaActual = tablero.get_casilla(accionProducir.getCasillaActualX(),accionProducir.getCasillaActualY());
			casillaSeleccionada = tablero.get_casilla(accionProducir.getCasilla_seleccionadaX(), accionProducir.getCasilla_seleccionadaY());
			Unidad unidad = DatosGlobales.unidades.get(accionProducir.getNombreUnidad());
			cp.producir_unidad(casillaActual, unidad, casillaSeleccionada);
		}
		
		else if(accion instanceof AccionAlojar) {
			AccionAlojar accionAlojar = (AccionAlojar) accion;	
			casillaActual = tablero.get_casilla(accionAlojar.getCasillaActualX(), accionAlojar.getCasillaActualY());
			casillaSeleccionada = tablero.get_casilla(accionAlojar.getCasillaSeleccionadaX(), accionAlojar.getCasillaSeleccionadaY());
			cp.alojar(casillaSeleccionada.getEdificio(), casillaActual, casillaSeleccionada);
		}
		else if(accion instanceof AccionDesalojar) {
			AccionDesalojar accionDesalojar = (AccionDesalojar) accion;
			casillaActual = tablero.get_casilla(accionDesalojar.getCasillaActualX(), accionDesalojar.getCasillaActualY());
			casillaSeleccionada = tablero.get_casilla(accionDesalojar.getCasillaSeleccionadaX(), accionDesalojar.getCasillaSeleccionadaY());
			cp.desalojar(casillaActual.getEdificio(), casillaSeleccionada, casillaActual,accionDesalojar.getNumUnidad());
		}
		else if(accion instanceof AccionAndar) {
			AccionAndar accionAndar = (AccionAndar) accion;
			casillaActual = tablero.get_casilla(accionAndar.getCasillaActualX(), accionAndar.getCasillaActualY());
			

			casillaSeleccionada = tablero.get_casilla(accionAndar.getCasillaSeleccionadaX(), accionAndar.getCasillaSeleccionadaY());
			
			cp.casillaDestino = casillaSeleccionada;
			cp.casilla_actual = casillaActual;
			String direccion = tablero.obtener_direccion(casillaSeleccionada, casillaActual);
			cp.crear_accion_andar(direccion);

		}
		else if(accion instanceof AccionCorrer) {
			AccionCorrer accionCorrer = (AccionCorrer) accion;
			casillaActual = tablero.get_casilla(accionCorrer.getCasillaActualX(), accionCorrer.getCasillaActualY());
			ArrayList<Casilla> camino = new ArrayList<Casilla>();
			for(Double[] matriz: accionCorrer.getCamino()) 
				camino.add(tablero.get_casilla(matriz[0], matriz[1]));
						
			cp.controlador_movimientos.crear_accion_correr(casillaActual, DatosGlobales.PASOS_CORRER, accionCorrer.getDirecciones(),camino);
		}
		else if(accion instanceof AccionHabilidad) {
			AccionHabilidad accionHabilidad = (AccionHabilidad) accion;
			casillaActual = tablero.get_casilla(accionHabilidad.getCasillaActualX(), accionHabilidad.getCasillaActualY());
			Habilidad habilidad = null;
			for(Habilidad hab : casillaActual.getUnidad().getHabilidades())
				if(hab.getNombre().equals(accionHabilidad.getHabilidad())) {
					habilidad = hab;
					break;
				}		
			
			if(habilidad instanceof HabilidadDiana) {
				AccionHabilidadDiana diana = (AccionHabilidadDiana)accionHabilidad;
				casillaSeleccionada = tablero.get_casilla(diana.getCasillaSeleccionadaX(), diana.getCasillaSeleccionadaY());
				((HabilidadDiana) habilidad).establecer_valores(casillaActual, casillaSeleccionada, diana.getDireccion());
				cp.ejecutar_habilidad((HabilidadDiana) habilidad);
			}
			else if(habilidad instanceof HabilidadArea) {
			AccionHabilidadArea area = (AccionHabilidadArea) accionHabilidad;	
			casillaActual = tablero.get_casilla(area.getCasillaActualX(), area.getCasillaActualY());			
			((HabilidadArea)habilidad).establecer_valores(casillaActual, tablero.getCasillas().values());			
				cp.ejecutar_habilidad(((HabilidadArea) habilidad));
			}
			else if(habilidad instanceof HabilidadLineal) {
				AccionHabilidadLineal linea = (AccionHabilidadLineal)accionHabilidad;
				casillaActual = tablero.get_casilla(linea.getCasillaActualX(), linea.getCasillaActualY());
				HabilidadLineal hab = (HabilidadLineal) habilidad;
				hab.establecer_valores(casillaActual, tablero.obtener_linea_casillas_adyacentes(casillaActual, linea.getDireccion(),hab.getDistancia()));
								
				cp.ejecutar_habilidad(hab);			
			}
		}
		else if(accion.getAccion().equals(DatosGlobales.ACCION_TURNO_TERMINADO)) { 
		miTurno = true;
		cp.comenzar_turno();
		System.out.println("JODER EMOS LLAMADO AL COMENZAR TURNO OSTIA PUTA ME CAGO EN DIOSSOOOOOOOOOS");
		}
		
	}
	
	private void cargar_partida() {
		ConstructorPartida constructor = new ConstructorPartida("pradera",rol);
		constructor.mostrar();
		cp = constructor.cp;
	//	cp.comunicador = this;
	}
	
}
