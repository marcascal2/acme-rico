DECISIONES MÁS SIGNIFICATIVAS: v2.0

	1. APIS:
		
		- En cuanto a la primera idea que teníamos sobre la implementación de la API de Telegram, tuvimos que desechar la idea al darnos cuenta de que no sería posible 
		completarla. Por tanto, decidimos intentar implementar GoogleDrive y alguna relacionada con un cambio de moneda, por la relación con el tema de nuestro proyecto, 
		que finalmente sería la de una web llamada ExchangeRate, con una API sencilla y sin necesidad de api-key.
		
		- Puestos manos a la obra, advertimos la dificultad de incorporar GoogleDrive a nuestro proyecto, y tras unos días de investigación, cambiamos definitivamente a 
		la API de Dropbox, actualmente implementada, que nos dejaría cubierta la integración de un método post.
		
		- En cuanto a la incorporación de un método get, con la API de ExchangeRate, no supuso al principio mayor dificultad, aunque a la hora de realizar los tests 
		nos dimos cuenta de los pequeños huecos que habíamos dejado sin comprobar y que, de hecho, daban fallo. Tras algunas modificaciones causadas por las respuestas de las
		llamadas que partían de la moneda EUR, pudimos completar también esta implementación.
		
	2. DECISIONES DE GESTIÓN Y PLANIFICACIÓN:
		
		- En un principio, quedaron todas las tareas repartidas y asignadas por parejas, sin embargo, a lo largo del desarrollo, fuimos modificando y adaptando 
		esta planificación inicial a nuestra propia forma de trabajo y gestión de tiempo de cada miembro, quedando así algunas tareas por parejas y la mayoría 
		individuales.
		
		- Tras completar y  entender el manejo de Travis en nuestro proyecto, decidimos cambiar la gestión de acceso y seguridad de GitHub quedando así establecido que 
		ningún merge se realizaría si no cumplía con dos condiciones esenciales. Primera, la rama nueva debería pasar los tests en Travis, algún fallo en tests requeriría 
		la modificación y corrección de la misma, y segunda, solo el administrador tendría permiso para realizar dicha acción que se completaría con una aprobación manual de ésta.
		Gracias a esta decisión, nos aseguraríamos del buen avance tanto de tests como de código en la rama master.
                
                - Debido a la cantidad de implementación tanto de funcionalidad como de tests del sprint 3 (segundo entregable), decidimos hacer uso
                de algunos recursos que nos proporciona GitHub, como los milestones y los labels, clasificando así nuestras tareas en tres categorías, prioridad alta, prioridad media y prioridad baja.
                Con esto incrementamos el rendimiento del equipo, aumentando así la eficiencia del avance y desarrollo del proyecto.
		
	3. IMPLEMENTACIÓN Y CLASES:
		
		- A lo largo del desarrollo del proyecto, nos dimos cuenta de que nuestro planteamiento acerca de las clases Transfer y TransferApp era prácticamente el mismo para ambas,
		es decir, tenían las mismas propiedades y relaciones, por lo que concluimos en dejar únicamente la clase TransferApp, ya que era la única de la que hacíamos un list y un update,
		las transferencias en sí quedaban constatadas a la hora de la suma y resta del dinero en las cuentas, pero en ningún sitio más.
                
        4. TESTS DE SERVICIO MYSQL/HSQLDB:
        
                - Los tests de servicio funcionaban perfectamente sobre hsqldb, pero al correrlos contra una bbdd MySQL nos daba problemas con los @BeforeEach. 
                Con el fin de no tener que rehacer nuestros tests intentamos probar con anotaciones como @Transactional (No servía) y con @DirtiesContext (Tardaba mucho en ejecutar). 
                Finalmente nos decantamos por tratar de implementar la gestión de perfiles que proporciona maven haciendo uso de anotaciones como @Profile y @ActiveProfile, 
                y a pesar de que al ejecutar los tests con un perfil los logs de los mismos indicaban que el perfil estaba activo, las anotaciones no surtían efecto. 
                Por esta razón, tras consensuarlo con Don Carlos Müller, decidimos marcar como @Disabled los tests que corren sobre hsqldb y solo ejecutar los que prueban sobre MySQL a la hora de seguir desarrollando.
		
