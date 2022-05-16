package jdbc;

import exceptions.RequiredFieldNotSetException;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public final class StatementProperty {
    public List<String> tableName = new ArrayList<>();
    public List<String> fields = new ArrayList<>();
    public List<String> criteria = new ArrayList<>();
    public SqlConsumer<PreparedStatement> valuesSetter;


    private StatementProperty() {}

    public static class Builder {

        private StatementProperty property = new StatementProperty();

        public Builder tableName(String... tableName) {
            property.tableName.addAll(List.of(tableName));
            return this;
        }

        public Builder fields(String... fields) {
            property.fields.addAll(List.of(fields));
            return this;
        }

        public Builder criteria(String... criteria) {
            property.criteria.addAll(List.of(criteria));
            return this;
        }

        public Builder valuesSetter(SqlConsumer<PreparedStatement> valuesSetter){
            property.valuesSetter = valuesSetter;
            return this;
        }

        public StatementProperty build() {
            checkRequiredFields();
            return property;
        }


        private void checkRequiredFields() {
            if (property.tableName == null)
                throw new RequiredFieldNotSetException("tableName");

            if(property.valuesSetter == null)
                property.valuesSetter = (s) -> {};
        }

    }
}
