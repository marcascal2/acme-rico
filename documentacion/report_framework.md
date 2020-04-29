REPORT DE PROBLEMAS CON EL FRAMEWORK: v1.0

	- Al intentar realizar ciertos tests de servicio usando Mock, nos damos cuenta de que realmente no comprueban gran cosa, ya que solo nos permiten comprobar que se ha pasado por una determinada línea de código. Sí son efectivos, sin embargo, para comprobar métodos con bucles.

	- Los métodos que requieren varias llamadas a la hora de comprobar su funcionamiento no pueden ser comprobados con tests de controlador ya que estos tests comprueban una secuancia de pasos a seguir, pero no permiten realizar varias llamadas en un mismo método ni cuentan con ninguna opción que nos permita establecer el número de llamadas a realizar sobre una misma url.

	- Al usar la notación @BeforeEach en los tests de servicio, encontramos problemas para correrlos sobre la base de datos de producción, ya que los datos son persistentes y se violan unicidades de claves primarias.
