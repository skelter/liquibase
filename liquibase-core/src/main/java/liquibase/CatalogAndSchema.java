package liquibase;

import liquibase.database.Database;

public class CatalogAndSchema {
    private String catalogName;
    private String schemaName;
    public static final CatalogAndSchema DEFAULT = new CatalogAndSchema(null, null);

    public CatalogAndSchema(String catalogName, String schemaName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public boolean equals(CatalogAndSchema catalogAndSchema, Database database) {
        return this.equals(catalogAndSchema); //todo: use database param or remove method
    }

    @Override
    public String toString() {
        if (catalogName == null && schemaName == null) {
            return "DEFAULT";
        }
        if (catalogName != null && schemaName == null) {
            return catalogName;
        }
        if (catalogName == null && schemaName != null) {
            return schemaName;
        }
        return catalogName+"."+schemaName;
    }
}
