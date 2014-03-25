package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.H2Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.statement.core.FindForeignKeyConstraintsStatement;

public class FindForeignKeyConstraintsGeneratorH2 extends AbstractSqlGenerator<FindForeignKeyConstraintsStatement> {
	@Override
	public int getPriority() {
		return PRIORITY_DATABASE;
	}

	@Override
	public boolean supports(FindForeignKeyConstraintsStatement statement, Database database) {
		return database instanceof H2Database;
	}

	@Override
  public ValidationErrors validate(FindForeignKeyConstraintsStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
		ValidationErrors validationErrors = new ValidationErrors();
		validationErrors.checkRequiredField("baseTableName", statement.getBaseTableName());
		return validationErrors;
	}

	
	@Override
  public Sql[] generateSql(FindForeignKeyConstraintsStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

	 //https://github.com/jOOQ/jOOQ/issues/3042  this wasn't helpful but it lead me to come up with this:
   // SELECT * FROM INFORMATION_SCHEMA.CONSTRAINTS c join INFORMATION_SCHEMA.INDEXES i on  c.UNIQUE_INDEX_NAME=i.INDEX_NAME 

	  StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("c.TABLE_NAME as ").append(FindForeignKeyConstraintsStatement.RESULT_COLUMN_BASE_TABLE_NAME).append(", ");
		sb.append("c.COLUMN_LIST as ").append(FindForeignKeyConstraintsStatement.RESULT_COLUMN_BASE_TABLE_COLUMN_NAME).append(", ");
		sb.append("i.TABLE_NAME as ").append(FindForeignKeyConstraintsStatement.RESULT_COLUMN_FOREIGN_TABLE_NAME).append(", ");
		sb.append("i.COLUMN_NAME as ").append(FindForeignKeyConstraintsStatement.RESULT_COLUMN_FOREIGN_COLUMN_NAME).append(", ");
		sb.append("c.CONSTRAINT_NAME as ").append(FindForeignKeyConstraintsStatement.RESULT_COLUMN_CONSTRAINT_NAME).append(" ");
		sb.append("FROM INFORMATION_SCHEMA.CONSTRAINTS c join INFORMATION_SCHEMA.INDEXES i on  c.UNIQUE_INDEX_NAME=i.INDEX_NAME  ");
		sb.append("WHERE c.TABLE_NAME = '").append(statement.getBaseTableName().toUpperCase()).append("'");

		return new Sql[] { new UnparsedSql(sb.toString()) };
	}
}
