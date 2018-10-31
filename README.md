# computer-database

## Introduction

*Your customer requested to build a computer database application. He owns about 500+ computers made by* *different manufacturers (companies such as Apple, Acer, Asus...).*
*Ideally, each computer would contain the following: a name, the date when it was introduced, eventually the* *date when it was discontinued, and the manufacturer. Obviously, for some reasons, the existing data is* *incomplete, and he requested that only the name should remain mandatory when adding a computer, the other* *fields being filled when possible. Furthermore, the date it was discontinued must be greater than the one he* *was introduced. The list of computers can be modified, meaning your customer should be able to list, add,* *delete, and update computers. The list of computers should also be pageable.*
*The list of companies should be exhaustive, and therefore will not require any update, deletion etc... *

## Features

* List computers
* List companies
* Show computer details (the detailed information of only one computer)
* Filter computers by name
* Create a computer
* Update a computer
* Delete a computer

## Maven dependencies

* junit: 4.12
* mysql: 8.0.12
* slf4j: 1.7.25
* jstl: 1.2
* servlet: 2.0
* jetty: 9.4.12.v20180830
* selenium: 3.14.0
* jquery-validation: 1.17.0-1