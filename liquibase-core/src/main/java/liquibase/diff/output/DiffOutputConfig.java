package liquibase.diff.output;

public class DiffOutputConfig {
    private boolean includeSchema;
    private boolean includeCatalog;
    private boolean includeTablespace;

    //TODO: Add logic to determine whether Catalog and Schema need to be set instead of always 
    //returning 'true.' Currently setting it for all objects in generateChangeLog.  You have to 
    //manually remove schema and catalog attributes if Change Log is used for a different 
    //schema or catalog.
    //Pete Pickerill, Datical, Inc.
    public DiffOutputConfig() {
        includeSchema = false;
        includeCatalog = false;
        includeTablespace = true;
    }

    public DiffOutputConfig(boolean includeCatalog, boolean includeSchema, boolean includeTablespace) {
        this.includeSchema = includeSchema;
        this.includeCatalog = includeCatalog;
        this.includeTablespace = includeTablespace;
    }

    public boolean isIncludeSchema() {
        return includeSchema;
    }

    public DiffOutputConfig setIncludeSchema(boolean includeSchema) {
        this.includeSchema = includeSchema;
        return this;
    }

    public boolean isIncludeCatalog() {
        return includeCatalog;
    }

    public DiffOutputConfig setIncludeCatalog(boolean includeCatalog) {
        this.includeCatalog = includeCatalog;
        return this;
    }

    public boolean isIncludeTablespace() {
        return includeTablespace;
    }

    public DiffOutputConfig setIncludeTablespace(boolean includeTablespace) {
        this.includeTablespace = includeTablespace;
        return this;
    }
}
