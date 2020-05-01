FALLOS CORREGIDOS GRACIAS A LOS TESTS: v1.1

	-Corrección del método setMoney() del servicio de TransferApp, donde debía comprobarse que la BankAccount destino estuviera o no en nuestra base de datos. Se comprobaba si el número de cuenta era nulo, pero no la entidad buscada. Gracias a los tests de este servicio.
	
	-Actualización del método setMoney() del servicio de TransferApp, que no comprobaba si la cantidad en la cuenta origen era suficiente para realizar la tranferencia, y la cantidad final no podía ser negativa. Gracias a los casos negativos del test de este servicio.
	
	-Corrección del método de creación y registro de una TransferApp en el controlador, que necesitaba realizar el setteo de la entidad BankAccount en la transferencia creada tras la comprobación de que la cantidad enviada no fuera nula. Gracias a los tests de dicho controlador.
	
	-Modificación de las rutas de los métodos de aceptar y rechazar una transferencia del controlador, añadiendo "/" al principio de cada una, ya que impedían que los tests puedieran comprobar dichos métodos correctamente.
	
	-Corrección del controlador de la API de ExchangeRate gracias a los tests de esta. Se tuvo que tener en cuenta si las llamadas partían de la moneda EUR, lo cuál implicaba el setteo de Double manualente, ya que la respuesta con dicha llamada no contaba con este valor, sin embargo, para cualquier otro tipo de moneda sí.
        
     -Corrección del registro de un empleado. Al añadir un nuevo empleado con un username ya registrado redirigía a la página oups en vez de mostrar el error en el formulario.
     
     -Corrección de algunos tests de controlador gracias a los tests de integración correspondientes a estos. En particular dos de ClientControllerTest que, aunque le faltaban algunos atributos, no daban error al ejecutarlos y en integración hemos identificado dichos errores.
