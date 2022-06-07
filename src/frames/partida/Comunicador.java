package frames.partida;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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


//WARNING : Se debe crear un subhilo que se encuentre en bucle permanente a la escucha de acciones por parte del ServerSocket. Si se encargase el hilo principal fx,
//daría como resultado un crasheo colosal y el apagado del equipo. Java fx no está pensado para cooperar con otros hilos.
public class Comunicador  {

	private final String HOST = "HOST";
	private final String CLIENTE = "CLIENTE";
	private String rol;
	private ServerSocket server;
	private Socket conexion;
	private ControladorPartida cp;
	private String ip;
	private int puerto;
	private volatile boolean miTurno;
	
	private int puertoHost = 9884;
	private int puertoCliente = 9885;
	private boolean soyHost;
	private boolean fin;
	private Accion accion;
	private Task<Void> tareaEscucha;
	
	public boolean miTurno() {
		return miTurno;
	}
	public Comunicador(int puerto) {
		this.puerto = puerto;
		this.fin = false;
		this.rol = HOST;
		tareaEscucha = new Task<Void>() {

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
	public Comunicador(String ip, int puerto) {
			this.ip = ip;
			this.puerto = puerto;
			this.fin = false;
			this.rol = CLIENTE;
			tareaEscucha = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					while(!fin) {
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
					return null;
					
				}};
	}
	
	public void buscar_partida() throws IOException {
		if(rol.equals(CLIENTE)) {
			//Rol de cliente
			conexion = new Socket(ip,puertoHost);
			System.out.println("Cliente conectado");
		
			miTurno = false;
			conexion.close();
			server = new ServerSocket(puertoCliente);
			iniciar_partida();
			//El cliente comienza segundo
			cp.turno = false;
	    	cp.tabla_inferior.setVisible(false);    	

			Thread hilo = new Thread(tareaEscucha);
			hilo.setDaemon(true);
			hilo.start();
			soyHost=false;
		}
		else if(rol.equals(HOST)) {
				server = new ServerSocket(puertoHost);
				System.out.println("Host creado");
				conexion = server.accept();
				System.out.println("Cliente conectado");
				conexion.close();
				miTurno = true;
				soyHost = true;
			

				iniciar_partida();
				cp.turno = true;
				Thread hilo = new Thread(tareaEscucha);
				hilo.setDaemon(true);
				hilo.start();
		}			
	}	

	/*Para evitar que otros hilos interaccionen con la interfaz del hijo fx, estos solo recibirán la accion y el hilo fx se encargará de ejecutarla.*/
	//No puede acceder a la variable miTurno porque esta siendo usado por el servidor en bucle al igual que la variable accion, estos se sustituyen por escuchaFX y accionRecibida

	public void fin() {
			fin = true;		
	}
	public void terminar_turno() {
		System.out.println("terminar turno del comunicador");
		miTurno = false;
		Accion accion = new Accion(null,DatosGlobales.ACCION_TURNO_TERMINADO);
		enviar_accion(accion);
	//	Thread hilo = new Thread(tareaEscucha);
	//	hilo.start();
	

	}
	public void enviar_accion(Accion accion) {
		try {
			if(soyHost)
			conexion = new Socket(ip,puertoCliente);
			else {
				conexion = new Socket(ip,puertoHost);
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
		}
		
	}
	
	private void iniciar_partida() {
		ConstructorPartida constructor = null;
		if(soyHost)
		 constructor = new ConstructorPartida("pradera",HOST);
		else
			 constructor = new ConstructorPartida("pradera",CLIENTE);
		constructor.mostrar();
		cp = constructor.cp;
		cp.comunicador = this;
	}
	

	
}
