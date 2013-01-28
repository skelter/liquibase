package liquibase.structure.core;

import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;

public class Sequence extends AbstractDatabaseObject {

    public DatabaseObject[] getContainingObjects() {
        return null;
    }

    public String getName() {
        return getAttribute("name", String.class);
    }

    public Sequence setName(String name) {
        this.setAttribute("name", name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sequence sequence = (Sequence) o;

        return !(getName() != null ? !getName().equalsIgnoreCase(sequence.getName()) : sequence.getName() != null);

    }

    @Override
    public int hashCode() {
        return (getName() != null ? getName().toUpperCase().hashCode() : 0);
    }


    @Override
    public String toString() {
        return getName();
    }

	/**
	 * @return Returns the schema.
	 */
	public Schema getSchema () {
		return getAttribute("schema", Schema.class);
	}

	/**
	 * @param schema The schema to set.
	 */
	public Sequence setSchema (Schema schema) {
		this.setAttribute("schema", schema);
        return this;
	}
}
