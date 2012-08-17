## Building Liquibase ##
Liquibase core is currently built using Maven 3.  Liquibase's modules are organized as follows

/                               Liquibase Parent Configuration pom
+ liquibase-core                       
+ liqubase-integration-tests   
+ liquibase-maven-plugin      
+ liquibase-osgi
+ samples
   + liquibase-ext-change
   + liquibase-ext-changewithnestedtags
   + liquibase-ext-sqlgenerator


Building with the <code>maven package</code> command will compile, run
tests and build the packages.

```
$ mvn package
[INFO] Reactor Summary:
[INFO] 
[INFO] Liquibase Parent Configuration
[INFO] Liquibase Core
[INFO] Liquibase Maven Plugin
[INFO] Liquibase Osgi
[INFO] Liquibase extension sample change
[INFO] Liquibase extension sample using nested tags for change
[INFO] Liquibase extension showing sample sqlgenerator
[INFO] Liquibase Integration Tests
```

## Core Subsystems by Function ##

*  DB Refactorings - ChangeLogs, changes and preconditions; parsers and serializers.
   *   liquibase.change
   *   liquibase.changelog
   *   liquibase.parser
   *   liquibase.serializer
   *   liquibase.precondition
   *   liquibase.datatype
   *   liquibase.sqlgenerator
   *   liquibase.statement
   *   liquibase.sql
*  Snapshots and Diffs
   *   liquibase.diff
   *   liquibase.snapshot
*  DB Interfacing - connections, database-specific implementations of changes and SQL, executors and the locking service.
   *   liquibase.database
       * core
       * jvm
       * structure
   *   liquibase.executor
   *   liquibase.lockservice
*  DB Doc - generation of beautiful, meaningful documentation
   * liquibase.dbdoc
*  Integration - Using Liquibase from various build systems, J2EE containers, dependency injection and command line.
   * liquibase.integration
      * ant
      * cdi
      * commandline
      * servlet
      * spring
*  Internals - programmatic necessities having little to do with databases:
   logging, resource loading, service location, file io, etc.
   *  liquibase.logger
   *  liquibase.resource
   *  liquibase.servicelocator
   *  liquibase.utils
   *  liquibase.exception
*  Primary programatic entry
   * liquibase.Liquibase

## Known Issues ##

