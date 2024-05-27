# Tutorial enlazar a proyecto Acme-SF

 Para conectar nuestro proyecto de Anti-Spam con el proyecto de Acme-SF es necesario entrar Properties->Java Build Path->Projects y dentro de Classpath pulsar Add y seleccionar nuestro proyecto Anti-Spam

 Aquí se enlaza un video para la instalación y demostración de su funcionamiento: https://www.youtube.com/watch?v=Kv_gAuIDelw

## Contenido importante
 Dentro de la carpeta src\main\webapp\WEB-INF\views se encuentra la carpeta master, dicha carpeta se debe mover a la misma ruta correspondiente en el proyecto de Acme-SF para proporcionar unos mensajes de error personalizados para el Spam

 Dentro de la carpeta src\main\webapp\WEB-INF\resources\sample-data se encuentran ficheros csv con los datos iniciales relacionados a las entidades de spam. Dichos ficherso se deben mover a la misma ruta correspondiente en el proyecto de Acme-SF para que a la hora de popular se metan dichos datos a la base de datos

## Explicación de funcionamiento
 El funcionamiento de este proyecto consiste en la creación de una clase abstracta que extiende del AbstractService proporcionado por Acme-Framework. Dicha clase es nombrada como AbstractAntiSpamService, y su implementación se realiza extendiendo los servicios que queramos que implementen el antispam con dicha clase además de una línea en validate que contenga la función "super.validateSpam(object)", siendo el método validateSpam quien se encarga de ver si los atributos tipo String del objeto seleccionado contienen spam. Dicha clase será útil para los servicios relacionados a la creación, actualización y publicación de las entidades existentes.
