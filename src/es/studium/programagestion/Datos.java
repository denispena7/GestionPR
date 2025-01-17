//DATOS

package es.studium.programagestion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class Datos {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/gestionbd";
	String login = "Admin";
	String password = "admin";

	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	 
	

	Datos() {
	}

	public boolean conectar() {
		boolean conexionCorrecta = true;
	// Cargar el Driver
		try {
			Class.forName(driver);
			System.out.println("Se ha cargado el driver correctamente");
		} catch (ClassNotFoundException e) {
			System.out.println("Se ha producido un error al cargar el Driver");
			conexionCorrecta = false;
		}

	// Establecer la conexión con la base de datos
		try {
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conectado");
		} catch (SQLException e) {
			System.out.println("Se produjo un error al conectar a la Base de Datos");
			conexionCorrecta = false;
		}
		return conexionCorrecta;
	}
	
	//Comprobacion de Credenciales

		public boolean comprobarCredenciales(String usuario, String password) {
			boolean credencialesCorrectas = true;
			String sentencia = "SELECT * FROM Usuario " + "WHERE nombreUsuario='" + usuario + "' "
					+ "AND passwordUsuario = SHA2('" + password + "', 256);";
			System.out.println(sentencia);

			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery(sentencia);

				if (!rs.next()) {
			// Credenciales incorrectas
				credencialesCorrectas = false;
				}
			}

			catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());

			}

		return credencialesCorrectas;
	}
		
	//Desconectar

	public void desconectar() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar " + e.toString());
		}
		}
	// CONSULTAS(COMPLETADAS)

	// Consulta Empleado
		public String ConsultaEmpleado() {
			String contenido = "";
			String sentencia = "SELECT * FROM empleado;";

			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery(sentencia);

			while (rs.next()) {
					contenido = contenido + rs.getString("idEmpleado") + "-" + rs.getString("nombreEmpleado") + "-"
						+ rs.getString("apellidoEmpleado") + "-" + rs.getString("puestoEmpleado") + "\n";
			}
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}

			return contenido;
		}

	// Consulta Tickets
		public String formatofecha(Date fecha) {
			SimpleDateFormat fechaNueva= new SimpleDateFormat("dd/mm/yyyy");
		return fechaNueva.format(fecha);
		}
		
		public String ConsultaTickets() {
			String contenido = "";
			String sentencia = "SELECT * FROM tickets;";

			try {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = statement.executeQuery(sentencia);

			while (rs.next()) {
				Date fecha=rs.getDate("fechaTickets");
				String nuevaFecha=formatofecha(fecha);
					contenido += contenido 
						+ rs.getString("idTickets") + "-" 
						+ rs.getString("descripcionTickets") 
						+ "-"
						+nuevaFecha
						+ "-" + rs.getString("importeTickets") 
						+ "-"
						+ "-" + rs.getString("idEmpleadoFK") + "\n";
			}
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}

				return contenido;
			}
	
	// Consulta Usuario
		public String ConsultaUsuario() {
			String contenido = "";
			String sentencia = "SELECT * FROM usuario;";

			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery(sentencia);

			while (rs.next()) {
					contenido = contenido 
							+ rs.getString("idUsuario") + "-" 
							+ rs.getString("nombreUsuario") +"-"
							+ rs.getString("claveUsuario") + "\n";
			}
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}

			return contenido;
		}

	// Consulta Articulo
		public String ConsultaArticulo() {
			String contenido = "";
			String sentencia = "SELECT * FROM articulos;";

			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery(sentencia);

			while (rs.next()) {
					contenido = contenido + rs.getString("idArticulo") + "-" + rs.getString("nombreArticulo") + "-"
						+ rs.getString("descripcionArticulo") + "-" + rs.getFloat("precioArticulo") + "-"
						+ rs.getInt("stockArticulo") + "-" + "\n";
					}
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}

			return contenido;
		}

	// Consulta Compra
		public String ConsultaCompra() {
				String contenido = "";
				String sentencia = "SELECT * FROM compra;";

			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery(sentencia);

			while (rs.next()) {
					contenido = contenido + rs.getString("idCompra") + "-" + rs.getInt("idTicketsFK") + "-"
						+ rs.getInt("idArticulosFK") + "\n";
			}
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}

			return contenido;
			}
	// ALTAS

	//  Empleado
		public boolean empleadoAlta(String nombre, String apellido, String puesto) {
		boolean altaCorrecta = true;
		String sentenciaSQL = "INSERT INTO Empleado VALUES (NULL,'" + nombre + "', '" + apellido + "','" + puesto
				+ "');";
		System.out.println(sentenciaSQL);

		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentenciaSQL);
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL:" + e.toString());
			altaCorrecta = false;
		}

		return altaCorrecta;
		}

		// Tickets
		// Método para dar de alta un ticket
	    	public boolean ticketsAlta(String descripcion, String fecha, String importe, String idEmpleado) {
	        boolean altaCorrecta = true;

	        // Convertir fecha al formato MySQL
	        String fechaMySQL = fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);

	        String sentenciaSQL = "INSERT INTO tickets VALUES (NULL, '" + descripcion + "', '" + fechaMySQL + "', " + importe + ", " + idEmpleado + ");";
	        System.out.println(sentenciaSQL);

	        try {
	            statement = connection.createStatement();
	            statement.executeUpdate(sentenciaSQL);
	        } catch (SQLException e) {
	            System.out.println("Error en la sentencia SQL:" + e.toString());
	            altaCorrecta = false;
	        }

	        return altaCorrecta;
	    }
	    // Método para rellenar el Choice con los IDs de los empleados
	    public String[] rellenarChoiceEmpleados() {
	        String elementos = "Elegir un Empleado...*";
	        String sentencia = "SELECT idEmpleado FROM empleado;";

	        try {
	            statement = connection.createStatement();
	            ResultSet rs = statement.executeQuery(sentencia);

	            while (rs.next()) {
	                elementos += rs.getString("idEmpleado") + "*";
	            }
	        } catch (SQLException e) {
	            System.err.println(e);
	        }

	        return elementos.split("\\*");
	    }
	
	
			// Articulos
			// Método para dar de alta un artículo
		    public boolean articulosAlta(String nombreArticulo, String descripcionArticulo, float precioArticulo, int stockArticulo, int idTicketsFK) {
		        boolean altaCorrecta = true;
		        String sentenciaSQL = "INSERT INTO articulos VALUES (NULL, '" + nombreArticulo + "', '"+ descripcionArticulo  + "', " + precioArticulo+", " +stockArticulo + ", "+ idTicketsFK +  ");";
		        System.out.println(sentenciaSQL);

		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            statement.executeUpdate(sentenciaSQL);
		        } catch (SQLException e) {
		            System.out.println("Error en la sentencia SQL:" + e.toString());
		            altaCorrecta = false;
		        }

		        return altaCorrecta;
		    }

		    // Método para rellenar el Choice con los tickets
		    public String[] rellenarChoiceTicketsAlta() {
		        String elementos = "Elegir un Ticket...*";
		        String sentencia = "SELECT idTickets FROM tickets;";
		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            rs = statement.executeQuery(sentencia);
		            while (rs.next()) {
		                elementos += rs.getString("idTickets") + "*";
		            }
		        } catch (SQLException e) {
		            System.err.println(e);
		        }
		        return elementos.split("\\*");
		    }
		    
		    //COMPRAS
		    
		
		 // Método para dar de alta una compra
		    public boolean comprasAlta(String idCompra, String idTicketsFK, String idArticuloFK) {
		        boolean altaCorrecta = true;

		        String sentenciaSQL = "INSERT INTO compra VALUES (" + idCompra + ", " + idTicketsFK + ", " + idArticuloFK + ");";
		        System.out.println(sentenciaSQL);

		        try {
		            statement = connection.createStatement();
		            statement.executeUpdate(sentenciaSQL);
		        } catch (SQLException e) {
		            System.out.println("Error en la sentencia SQL:" + e.toString());
		            altaCorrecta = false;
		        }

		        return altaCorrecta;
		    }

		    // Método para rellenar el Choice con los tickets
		    public String[] rellenarChoiceTicketsAltaCompra() {
		        String elementos = "Elegir un Ticket...*";
		        String sentencia = "SELECT idTickets FROM tickets;";
		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            rs = statement.executeQuery(sentencia);
		            while (rs.next()) {
		                elementos += rs.getString("idTickets") + "*";
		            }
		        } catch (SQLException e) {
		            System.err.println(e);
		        }
		        return elementos.split("\\*");
		    }
		 // Método para rellenar el Choice con los tickets
		    public String[] rellenarChoiceArticuloAltaCompra() {
		        String elementos = "Elegir un Articulo...*";
		        String sentencia = "SELECT idArticulo FROM articulo;";
		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            rs = statement.executeQuery(sentencia);
		            while (rs.next()) {
		                elementos += rs.getString("idArticulo") + "*";
		            }
		        } catch (SQLException e) {
		            System.err.println(e);
		        }
		        return elementos.split("\\*");
		    }

		    // Método para rellenar el Choice con los artículos
		    public String[] rellenarChoiceArticulosAltaCompra() {
		        String elementos = "Elegir un Artículo...*";
		        String sentencia = "SELECT idArticulo FROM articulos;";
		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            rs = statement.executeQuery(sentencia);
		            while (rs.next()) {
		                elementos += rs.getString("idArticulo")  + "*";
		            }
		        } catch (SQLException e) {
		            System.err.println(e);
		        }
		        return elementos.split("\\*");
		    }
		//Usuario
		    
		    // Método para dar de alta un usuario
		    public boolean usuarioAlta(String nombre, String tipoUsuario, String password) {
		        boolean altaCorrecta = true;
		        String sentenciaSQL = "INSERT INTO usuarios (nombreUsuario, tipoUsuario, password) VALUES ('" + nombre + "', '" + tipoUsuario + "', '" + password + "');";
		        System.out.println(sentenciaSQL);

		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            statement.executeUpdate(sentenciaSQL);
		        } catch (SQLException e) {
		            System.out.println("Error en la sentencia SQL: " + e.toString());
		            altaCorrecta = false;
		        }

		        return altaCorrecta;
		    }
		
			    
			
	// BAJA
		    //Empleado
		    public String[] rellenarChoiceEmpleado() {
		        String elementosCadena = "Elegir un empleado...*";
		        String sentencia = "SELECT idEmpleado, CONCAT(nombreEmpleado, ' ', apellidoEmpleado) AS nombre_completo FROM empleado;";

		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            rs = statement.executeQuery(sentencia);

		            while (rs.next()) {
		                elementosCadena += rs.getString("idEmpleado") + "-" + rs.getString("nombre_completo") + "*";
		            }
		        } catch (SQLException sqle) {
		            System.out.println("Error en la sentencia SQL: " + sqle.toString());
		        }
		        return elementosCadena.split("\\*");
		    }

		    public boolean eliminarEmpleado(int idEmpleado, String usuario) {
		        boolean eliminado = false;
		        String sentencia = "DELETE FROM empleado WHERE idEmpleado = " + idEmpleado + ";";

		        try {
		            statement = connection.createStatement();
		            statement.executeUpdate(sentencia);
		            eliminado = true;
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return eliminado;
		    }
		

			
			    
			 
	// Baja Tickets
		    public String[] rellenarChoiceTickets() {
		        String elementosCadena = "Elegir un ticket...*";
		        String sentencia = "SELECT idTickets, descripcionTickets FROM tickets;";

		        try {
		            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		            rs = statement.executeQuery(sentencia);

		            while (rs.next()) {
		                elementosCadena += rs.getString("idTickets") + "-" + rs.getString("descripcionTickets") + "*";
		            }
		        } catch (SQLException sqle) {
		            System.out.println("Error en la sentencia SQL: " + sqle.toString());
		        }
		        return elementosCadena.split("\\*");
		    }

		    public boolean eliminarTicket(int idTicket, String usuario) {
		        boolean eliminado = false;
		        String sentencia = "DELETE FROM tickets WHERE idTickets = " + idTicket + ";";

		        try {
		            statement = connection.createStatement();
		            statement.executeUpdate(sentencia);
		            eliminado = true;
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return eliminado;
		    }
		
		// Baja Usuario
				public String[] rellenarChoiceUsuario() {

					String elementos = "Elegir un Usuario...*";
					String sentencia = "SELECT * FROM usuario;";

					try {
						statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs = statement.executeQuery(sentencia);
					while (rs.next()) {
							elementos = elementos
									+ rs.getString("idUsuario") 
									+ "-" + rs.getString("nombreUsuario") 
									+ "*";
						}
					} catch (SQLException e) {
						System.out.println(e);
					}
					return elementos.split("\\*");
				
				}
				//Metodo Eliminar Usuario 
				public void eliminarUsuario(int idUsuario, String usuario) {
				    String sentencia = "DELETE*FROM usuarios WHERE idUsuario = " + idUsuario + ";";
				    try {
				        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				        statement.executeUpdate(sentencia);
				        Utilidad.escrituraFicheroLog(usuario, sentencia);
				    } catch (SQLException sqle) {
				        Utilidad.escrituraFicheroLog(usuario, "Error en la sentencia SQL " + sqle.getMessage());
				        // System.out.println("Error en la sentencia SQL: " + sqle.toString());
				    }
				}
				
				
				// Baja Articulo
				public String[] rellenarChoiceArticuloEliminar() {
			        String elementos = "Elegir un Artículo...";
			        String sentencia = "SELECT idArticulo, nombreArticulo FROM articulos;";
			        try {
			            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			            rs = statement.executeQuery(sentencia);
			            if (rs.next()) {
			                do {
			                    elementos += rs.getString("idArticulo") + "-" + rs.getString("nombreArticulo") + "*";
			                } while (rs.next());
			            }
			        } catch (SQLException e) {
			            System.err.println("Error al obtener los artículos: " + e.getMessage());
			        } finally {
			            try {
			                if (rs!= null) {
			                    rs.close();
			                }
			                if (statement!= null) {
			                    statement.close();
			                }
			            } catch (SQLException e) {
			                System.err.println("Error al cerrar los recursos: " + e.getMessage());
			            }
			        }
			        return elementos.split("\\*");
			    }

			    public void eliminarArticulo(int idArticulo) {
			        String sentencia = "DELETE FROM articulos WHERE idArticulo = " + idArticulo + ";";
			        try {
			            statement = connection.createStatement();
			            statement.executeUpdate(sentencia);
			        } catch (SQLException e) {
			            e.printStackTrace();
			        } finally {
			            try {
			                if (statement!= null) {
			                    statement.close();
			                }
			            } catch (SQLException e) {
			                System.err.println("Error al cerrar los recursos: " + e.getMessage());
			            }
			           
			        }
			    }
			
				
				
				//Baja Compras
				
				 
				 // Método para rellenar el Choice con las compras
				    public String[] rellenarChoiceCompraBaja() {
				        String elementos = "Elegir una Compra...*";
				        String sentencia = "SELECT idCompra FROM compra;";
				        try {
				            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				            rs = statement.executeQuery(sentencia);
				            while (rs.next()) {
				                elementos += rs.getString("idCompra") + "*";
				            }
				        } catch (SQLException e) {
				            System.err.println("Error al obtener las compras: " + e.getMessage());
				        }
				        return elementos.split("\\*");
				    }

				    // Método para eliminar una compra
				    public boolean eliminarCompra(int idCompra, String usuario) {
				        boolean eliminado = false;
				        String sentencia = "DELETE FROM compra WHERE idCompra = " + idCompra + ";";

				        try {
				            statement = connection.createStatement();
				            statement.executeUpdate(sentencia);
				            eliminado = true;
				        } catch (SQLException e) {
				            e.printStackTrace();
				        }
				        return eliminado;
				    }
				
				//PDF
				public void process(Table tabla,String registro,PdfFont fuente, boolean Cabecera)
				{
					StringTokenizer tokenizer=new StringTokenizer(registro,"-");
					while(tokenizer.hasMoreTokens()) {
						if(Cabecera) {
							tabla.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(fuente)));
						}
						else {
							tabla.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(fuente)));
						}
					}
					

				}
//MODIFICACION
				
				//USUARIOS
				// Método para obtener la lista de usuarios
			    public String[] RellenarchcUsuarioMod() {
			        String elementos = "Elegir un Usuario...*";
			        String sentencia = "SELECT * FROM usuario;";

			        try {
			            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			            rs = statement.executeQuery(sentencia);
			            while (rs.next()) {
			                elementos = elementos + rs.getString("nombreUsuario") + "*";
			            }
			        } catch (SQLException e) {
			            System.out.println(e);
			        }
			        return elementos.split("\\*");
			    }

			    // Método para modificar el usuario
			    public boolean modificarUsuario(String nombreUsuario, String nuevoValor) {
			        boolean modificacionCorrecta = true;
			        String sentencia = "UPDATE usuario SET claveUsuario = SHA2('" + nuevoValor + "', 256) WHERE nombreUsuario = '" + nombreUsuario + "';";
			        System.out.println(sentencia);

			        try {
			            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			            statement.executeUpdate(sentencia);
			        } catch (SQLException e) {
			            System.out.println("Error en la sentencia SQL:" + e.toString());
			            modificacionCorrecta = false;
			        }

			        return modificacionCorrecta;
			    }
			
		
			//TICKETS
			    //Método para obtener la lista de tickets
			    public String[] rellenarChoiceTicketsMod() {
			    	String elementos = "Elegir un Ticket...*";
			    	String sentencia = "SELECT * FROM tickets;";

			    	try {
			    		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    		rs = statement.executeQuery(sentencia);
			    		while (rs.next()) {
			    			elementos = elementos 
			    					+ rs.getString("idTickets") + "-" 
			    					+ rs.getString("descripcionTickets") + "-" 
			    					+ rs.getString("fechaTickets") + "-" 
			    					+ rs.getString("importeTickets") + "*";
			    		}
			    	} catch (SQLException e) {
			    		System.out.println(e);
			    	}
			    	return elementos.split("\\*");
}
			 
			    // Método para modificar el ticket
			    	public boolean modificarTicket(String ticketSeleccionado, String nuevaDescripcion, String nuevoImporte) {
			    		boolean modificacionCorrecta = true;
			    		String idTicket = ticketSeleccionado.split("-")[0];
			    		String sentencia = "UPDATE tickets SET descripcionTickets = '" + nuevaDescripcion + "', importeTickets = " + nuevoImporte + " WHERE idTickets = " + idTicket + ";";
			    		System.out.println(sentencia);

			    		try {
			    			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    			statement.executeUpdate(sentencia);
			    		} catch (SQLException e) {
			    			System.out.println("Error en la sentencia SQL:" + e.toString());
			    			modificacionCorrecta = false;
			    		}

			    		return modificacionCorrecta;
			    	}
		//ARTICULOS
			    	  public String[] rellenarChoiceArticulosMod() {
			    	        String elementos = "Elegir un Artículo...*";
			    	        String sentencia = "SELECT * FROM articulos;";

			    	        try {
			    	            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	            rs = statement.executeQuery(sentencia);
			    	            while (rs.next()) {
			    	                elementos = elementos 
			    	                    + rs.getString("idArticulo") + "-" 
			    	                    + rs.getString("nombreArticulo") + "-" 
			    	                    + rs.getString("descripcionArticulo") + "-" 
			    	                    + rs.getString("precioArticulo") + "-" 
			    	                    + rs.getString("stockArticulo") + "*";
			    	            }
			    	        } catch (SQLException e) {
			    	            System.out.println(e);
			    	        }
			    	        return elementos.split("\\*");
			    	    }

			    	    public boolean modificarArticulo(String articuloSeleccionado, String nuevoNombre, String nuevaDescripcion, float nuevoPrecio, int nuevoStock) {
			    	        boolean modificacionCorrecta = true;
			    	        String idArticulo = articuloSeleccionado.split("-")[0];
			    	        String sentencia = "UPDATE articulos SET nombreArticulo = '" + nuevoNombre + "', descripcionArticulo = '" + nuevaDescripcion + "', precioArticulo = " + nuevoPrecio + ", stockArticulo = " + nuevoStock + " WHERE idArticulo = " + idArticulo + ";";
			    	        System.out.println(sentencia);

			    	        try {
			    	            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	            statement.executeUpdate(sentencia);
			    	        } catch (SQLException e) {
			    	            System.out.println("Error en la sentencia SQL:" + e.toString());
			    	            modificacionCorrecta = false;
			    	        }

			    	        return modificacionCorrecta;
			    	    }
			   //EMPLEADOS
			    	    //Rellenamos el choice
			    	    public String[] rellenarChoiceEmpleadosMod() {
			    	        String elementos = "Elegir un Empleado...*";
			    	        String sentencia = "SELECT * FROM empleado;";

			    	        try {
			    	            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	            rs = statement.executeQuery(sentencia);
			    	            while (rs.next()) {
			    	                elementos = elementos 
			    	                    + rs.getString("idEmpleado") + "-" 
			    	                    + rs.getString("nombreEmpleado") + "-" 
			    	                    + rs.getString("apellidoEmpleado") + "-" 
			    	                    + rs.getString("puestoEmpleado") + "*";
			    	            }
			    	        } catch (SQLException e) {
			    	            System.out.println(e);
			    	        }
			    	        return elementos.split("\\*");
			    	    }
			    	    
			    	    //Modificamos Empleado
			    	    public boolean modificarEmpleado(String empleadoSeleccionado, String nuevoNombre, String nuevoApellido, String nuevoPuesto) {
			    	        boolean modificacionCorrecta = true;
			    	        String idEmpleado = empleadoSeleccionado.split("-")[0];
			    	        String sentencia = "UPDATE empleado SET nombreEmpleado = '" + nuevoNombre + "', apellidoEmpleado = '" + nuevoApellido + "', puestoEmpleado = '" + nuevoPuesto + "' WHERE idEmpleado = " + idEmpleado + ";";
			    	        System.out.println(sentencia);

			    	        try {
			    	            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	            statement.executeUpdate(sentencia);
			    	        } catch (SQLException e) {
			    	            System.out.println("Error en la sentencia SQL:" + e.toString());
			    	            modificacionCorrecta = false;
			    	        }

			    	        return modificacionCorrecta;
			    	    }
			    	
			   //Compras
			    	    // Método para rellenar la lista de compras en la ventana de modificaciones
			    	    public String[] rellenarChoiceComprasMod() {
			    	        String elementos = "Elegir una Compra...*";
			    	        String sentencia = "SELECT * FROM compra;";

			    	        try {
			    	            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	            rs = statement.executeQuery(sentencia);
			    	            while (rs.next()) {
			    	                elementos = elementos
			    	                        + rs.getString("idCompra") + "-"+ rs.getString("idTicketsFK") + "-"+ rs.getString("idArticulosFK")+"*";
			    	                        // Aquí puedes añadir las otras columnas si las necesitas para la lista de compras
			    	            }
			    	        				} catch (SQLException e) {
			    	        					System.out.println(e);
			    	        				}
			    	        	return elementos.split("\\*");
			    	    }

			    	    // Método para modificar una compra
			    	    public boolean modificarCompra(String compraSeleccionada, String nuevoIdCompra) {
			    	        boolean modificacionCorrecta = true;
			    	        String idCompra = compraSeleccionada.split("-")[0];
			    	        String sentencia = "UPDATE compra SET idCompra = '" + nuevoIdCompra + "' WHERE idCompra = " + idCompra + ";";

			    	        try {
			    	            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    	            statement.executeUpdate(sentencia);
			    	        } catch (SQLException e) {
			    	            System.out.println("Error en la sentencia SQL: " + e.toString());
			    	            modificacionCorrecta = false;
			    	        }

			    	        return modificacionCorrecta;
			    	    }
			    	
//Ayuda 
//Método ayuda
public void ayuda()
{
    try
    {
        Runtime.getRuntime().exec("Ayuda.chm");
    }
    catch(IOException e)
    {
        e.printStackTrace();
    }
}
}			
				
				
	
				

