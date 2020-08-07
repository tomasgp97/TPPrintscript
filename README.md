# TPPrintscript

errors: no puedo testear nada desde el main por problemas con circular dependencies en el gradle build (no se que es)

Url solver: si reconoce el token URL a traves de la regex, descarga el archivo y lee y lexea este archivo agregandolo a la lista de tokens que van de input al parser. 
generator: se recorren las statements appendeando el valor a un string. hubiese sido mejor tener otro visitor y seguir la misma logica del interpreter pero en vez de ejecutar, devolver el valor en codigo
