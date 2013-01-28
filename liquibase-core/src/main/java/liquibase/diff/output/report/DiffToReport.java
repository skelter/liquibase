package liquibase.diff.output.report;

import liquibase.diff.Difference;
import liquibase.diff.ObjectDifferences;
import liquibase.structure.DatabaseObject;
import liquibase.diff.DiffResult;
import liquibase.diff.StringDiff;
import liquibase.exception.DatabaseException;

import java.io.PrintStream;
import java.util.*;

public class DiffToReport {

    private DiffResult diffResult;
    private PrintStream out;

    public DiffToReport(DiffResult diffResult, PrintStream out) {
        this.diffResult = diffResult;
        this.out = out;
    }

    public void print() throws DatabaseException {
        out.println("Reference Database: " + diffResult.getReferenceSnapshot().getDatabase());
        out.println("Comparison Database: " + diffResult.getComparisonSnapshot().getDatabase());

        printComparison("Product Name", diffResult.getProductNameDiff(), out);
        printComparison("Product Version", diffResult.getProductVersionDiff(), out);

        TreeSet<Class<? extends DatabaseObject>> types = new TreeSet<Class<? extends DatabaseObject>>(new Comparator<Class<? extends DatabaseObject>>() {
            public int compare(Class<? extends DatabaseObject> o1, Class<? extends DatabaseObject> o2) {
                return o1.getSimpleName().compareTo(o2.getSimpleName());
            }
        });
        types.addAll(diffResult.getCompareControl().getComparedTypes());
        for (Class<? extends DatabaseObject> type : types) {
            printSetComparison("Missing " + getTypeName(type), diffResult.getMissingObjects(type), out);
            printSetComparison("Unexpected "+getTypeName(type), diffResult.getUnexpectedObjects(type), out);

            printChangedComparison("Changed " + getTypeName(type), diffResult.getChangedObjects(type), out);

        }
        
//        printColumnComparison(diffResult.getColumns().getChanged(), out);
    }

    protected String getTypeName(Class<? extends DatabaseObject> type) {
        return type.getSimpleName().replaceAll("([A-Z])", " $1").trim() + "(s)";
    }

    protected void printChangedComparison(String title, Map<? extends DatabaseObject, ObjectDifferences> objects, PrintStream out) {
        out.print(title + ": ");
        if (objects.size() == 0) {
            out.println("NONE");
        } else {
            out.println();
            for (Map.Entry<? extends DatabaseObject, ObjectDifferences> object : objects.entrySet()) {
                if (object.getValue().hasDifferences()) {
                    out.println("     " + object.getKey());
                    for (Difference difference : object.getValue().getDifferences()) {
                        out.println("          " + difference.toString());
                    }
                }
            }
        }
    }

    protected void printSetComparison(String title, Set<?> objects, PrintStream out) {
        out.print(title + ": ");
        if (objects.size() == 0) {
            out.println("NONE");
        } else {
            out.println();
            for (Object object : objects) {
                out.println("     " + object);
            }
        }
    }

//    private void printColumnComparison(SortedSet<Column> changedColumns,
//                                       PrintStream out) {
//        out.print("Changed Columns: ");
//        if (changedColumns.size() == 0) {
//            out.println("NONE");
//        } else {
//            out.println();
//            for (Column column : changedColumns) {
//                out.println("     " + column);
//                Column baseColumn = diffResult.getReferenceSnapshot().getColumn(column.getRelation().getSchema(),
//                        column.getRelation().getName(), column.getName());
//                if (baseColumn != null) {
//                    if (baseColumn.isDataTypeDifferent(column)) {
//                        out.println("           from "
//                                + baseColumn.getType()
//                                + " to "
//                                + diffResult.getComparisonSnapshot().getColumn(column.getRelation().getSchema(), column.getRelation().getName(), column.getName()).getType());
//                    }
//                    if (baseColumn.isNullabilityDifferent(column)) {
//                        Boolean nowNullable = diffResult.getComparisonSnapshot().getColumn(column.getRelation().getSchema(),
//                                column.getRelation().getName(), column.getName())
//                                .isNullable();
//                        if (nowNullable == null) {
//                            nowNullable = Boolean.TRUE;
//                        }
//                        if (nowNullable) {
//                            out.println("           now nullable");
//                        } else {
//                            out.println("           now not null");
//                        }
//                    }
//                }
//            }
//        }
//    }

    protected void printComparison(String title, StringDiff string, PrintStream out) {
        out.print(title + ":");

        if (string == null) {
            out.print("NULL");
            return;
        }

        if (string.areEqual()) {
            out.println(" EQUAL");
        } else {
            out.println();
            out.println("     Reference:   '"
                    + string.getReferenceVersion() + "'");
            out.println("     Target: '" + string.getTargetVersion() + "'");
        }

    }

}
