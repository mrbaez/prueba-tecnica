# Prueba-tecnica

## Consigna

Desarrollar una API con Spring Boot que sea un wrapper de BraveNewCoin junto con su propio modelo de datos representado en Hibernate/JPA, los
requerimientos para esto son los siguientes:

* Se debe permitir la creación de usuarios, respondiendo a los siguientes requerimientos:
    * Usuarios:
        * Nombre
        * Apellido
        * Username (único)
        * Contraseña (8 caracteres, alfánumerica)
        * Moneda Local del usuario (puede ser euros o pesos colombianos)
        * Valor de la Tasa de Cambio USD a Moneda local del usuario (Ejemplo en Colombia TRM)

* La autenticación debe realizarse mediante tokens JWT:
    * El token se debe obtener mediante un endpoint de login (pasando un usuario y contraseña).
    * El token debe tener un tiempo de expiración.
    * Este token debe generarse desde la aplicación sin utilizar servicios externos (puede utilizarse packages que faciliten la creación del mismo
      pero no servicios como auth0).

* Se debe permitir agregar criptomonedas al usuario:
    * La relación usuario-criptomoneda debe ser única (contando la criptomoneda preferida).
    * Sólo un usuario puede agregarse criptomonedas a sí mismo.

* Endpoint para listar las criptomonedas del usuario:
    * Un usuario puede listar sus criptomonedas y no la de otro usuario.
    * Lo datos a incluir por cada moneda deben ser:
        * Precio (en la moneda del usuario).
        * Nombre.
        * Ranking en capitalización de Mercado.
* Endpoint para obtener el top 3 de criptomonedas de un usuario (comparando la cotización en la moneda preferida del usuario que realiza la consulta):
  * Debe encontrarse ordenada por valor (ascendente/descendente, descendente por default)
  * Debe especificar los mismos datos que el endpoint anterior:
    * Precio (en la moneda del usuario).
    * Nombre.
    * Ranking en capitalización de Mercado.
