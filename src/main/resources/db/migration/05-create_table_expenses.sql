--liquibase formatted sql

--changeset dsw:5
CREATE TABLE IF NOT EXISTS expenses (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    code character varying(36) not null,
    expense_name character varying(100) NOT NULL,
    expense_price double precision NOT NULL,

    CONSTRAINT pk_expenses PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users_expense (
    user_id integer NOT NULL,
    expense_id integer NOT NULL,
    CONSTRAINT user_id_fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT expenses_id_fk_expense FOREIGN KEY (expense_id) REFERENCES expenses (id)
 );

 COMMENT ON TABLE expenses IS 'This table provide basic informations about the expenses.';
 COMMENT ON COLUMN expenses.id IS 'Column responsible about informations about expenses`s ID.';
 COMMENT ON COLUMN expenses.code IS 'Column responsible about informations about expense`s code, or external ID.';
 COMMENT ON COLUMN expenses.expense_name IS 'Column responsible about model informations of expenses.';
 COMMENT ON COLUMN expenses.expense_price IS 'Column responsible about informations of maker of expenses.';
 COMMENT ON CONSTRAINT pk_expenses ON expenses IS 'Constraint responsible to guarantee the unike information on primary key of expense registry.';

 COMMENT ON TABLE users_expense IS 'This table provides basic information about the relationship between expenses and users.';
 COMMENT ON COLUMN users_expense.user_id IS 'Column responsible for user identification information.';
 COMMENT ON COLUMN users_expense.expense_id IS 'Column responsible for expense identification information.';
 COMMENT ON CONSTRAINT user_id_fk_user ON users_expense IS 'Constraint refer the foreign key relationship to users table.';
 COMMENT ON CONSTRAINT expenses_id_fk_expense ON users_expense IS 'Constraint refer the foreign key relationship to expenses table.';

ALTER TABLE IF EXISTS expenses OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS users_expense OWNER to "user-finApp-api-java";

 CREATE INDEX IF NOT EXISTS ix_expenses_id
     ON expenses USING btree
     (id ASC NULLS LAST)
     TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_users_expense_id
    ON users_expense USING btree
    (user_id ASC NULLS LAST)
    TABLESPACE pg_default;
