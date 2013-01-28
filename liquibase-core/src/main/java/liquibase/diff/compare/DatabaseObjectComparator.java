package liquibase.diff.compare;

import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.structure.DatabaseObject;

public interface DatabaseObjectComparator {

    final int PRIORITY_NONE = -1;
    final int PRIORITY_DEFAULT = 1;
    final int PRIORITY_TYPE = 5;
    final int PRIORITY_DATABASE = 10;

    int getPriority(Class<? extends DatabaseObject> objectType, Database database);

    boolean isSameObject(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, DatabaseObjectComparatorChain chain);

    ObjectDifferences findDifferences(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, DatabaseObjectComparatorChain chain);
}
