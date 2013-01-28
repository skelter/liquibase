package liquibase.change;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.exception.DatabaseException;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A common parent for all raw SQL related changes regardless of where the sql was sourced from.
 * 
 * Implements the necessary logic to choose how the SQL string should be parsed to generate the statements.
 *
 */
public abstract class AbstractSQLChange extends AbstractChange {

    private boolean stripComments;
    private boolean splitStatements;
    private String endDelimiter;
    private String sql;

    protected AbstractSQLChange() {
        stripComments= false;
        splitStatements =true;
    }

    /**
     * {@inheritDoc}
     * @param database
     * @return
     */
    @Override
    public boolean supports(Database database) {
        return true;
    }

    /**
     * Return if comments should be stripped from the SQL before passing it to the database.
     */
    public boolean isStrippingComments() {
        return stripComments;
    }

    /**
     * Return true if comments should be stripped from the SQL before passing it to the database.
     */
    public void setStripComments(Boolean stripComments) {
        this.stripComments = stripComments;
    }

    /**
     * Return if the SQL should be split into multiple statements before passing it to the database.
     * By default, statements are split around ";" and "go" delimiters.
     */
    public boolean isSplittingStatements() {
        return splitStatements;
    }

    /**
     * Set whether SQL should be split into multiple statements.
     */
    public void setSplitStatements(Boolean splitStatements) {
        this.splitStatements = splitStatements;
    }

    /**
     * Return the raw SQL managed by this Change
     */
    public String getSql() {
        return sql;
    }

    /**
     * Set the raw SQL managed by this Change.
     */
    public void setSql(String sql) {
       this.sql = StringUtils.trimToNull(sql);
    }

    /**
     * Set the end delimiter used to split statements. Will return null if the default delimiter should be used.
     *
     * @see #splitStatements
     */
    public String getEndDelimiter() {
        return endDelimiter;
    }

    /**
     * Set the end delimiter for splitting SQL statements. Set to null to use the default delimiter.
     * @param endDelimiter
     */
    public void setEndDelimiter(String endDelimiter) {
        this.endDelimiter = endDelimiter;
    }

    /**
     * Calculates the checksum based on the contained SQL.
     *
     * @see liquibase.change.AbstractChange#generateCheckSum()
     */
    @Override
    public CheckSum generateCheckSum() {
        String sql = getSql();
        if (sql == null) {
            sql = "";
        }
        return CheckSum.compute(this.endDelimiter+":"+
                this.isSplittingStatements()+":"+
                this.isStrippingComments()+":"+
                sql.replaceAll("\r\n", "\n").replaceAll("\r", "\n")); //normalize line endings
    }


    /**
     * Generates one or more SqlStatements depending on how the SQL should be parsed.
     * If split statements is set to true then the SQL is split and each cpommand is made a separate SqlStatement.
     * <p></p>
     * If stripping comments is true then any comments are removed before the splitting is executed.
     */
    public SqlStatement[] generateStatements(Database database) {

        List<SqlStatement> returnStatements = new ArrayList<SqlStatement>();

        if (StringUtils.trimToNull(getSql()) == null) {
            return new SqlStatement[0];
        }

        String processedSQL = getSql().replaceAll("\r\n", "\n").replaceAll("\r", "\n");
        for (String statement : StringUtils.processMutliLineSQL(processedSQL, isStrippingComments(), isSplittingStatements(), getEndDelimiter())) {
            if (database instanceof MSSQLDatabase) {
                 statement = statement.replaceAll("\n", "\r\n");
             }

            String escapedStatement;
			try {
				escapedStatement = database.getConnection().nativeSQL(statement);
			} catch (DatabaseException e) {
				escapedStatement = statement;
			}

            returnStatements.add(new RawSqlStatement(escapedStatement, getEndDelimiter()));
        }

        return returnStatements.toArray(new SqlStatement[returnStatements.size()]);
    }
}
