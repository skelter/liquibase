package liquibase.precondition.core;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.database.structure.Schema;
import liquibase.exception.*;
import liquibase.precondition.Precondition;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.DatabaseSnapshotGeneratorFactory;
import liquibase.util.StringUtils;

public class ViewExistsPrecondition implements Precondition {
    private String catalogName;
    private String schemaName;
    private String viewName;

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Warnings warn(Database database) {
        return new Warnings();
    }

    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }

    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet) throws PreconditionFailedException, PreconditionErrorException {
    	String currentSchemaName;
        String currentCatalogName;
    	try {
            currentCatalogName = getCatalogName();
            currentSchemaName = getSchemaName();
            if (!DatabaseSnapshotGeneratorFactory.getInstance().getGenerator(database).hasView(database.correctSchema(new Schema(currentCatalogName, currentSchemaName)), getViewName(), database)) {
                throw new PreconditionFailedException("View "+database.escapeTableName(currentCatalogName, currentSchemaName, getViewName())+" does not exist", changeLog, this);
            }
        } catch (PreconditionFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    public String getName() {
        return "viewExists";
    }
}