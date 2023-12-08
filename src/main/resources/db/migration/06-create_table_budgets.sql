--liquibase formatted sql

--changeset dsw:6
CREATE TABLE IF NOT EXISTS budgets (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    code character varying(36) not null,
    budget_name character varying(100) NOT NULL,
    budget_limit double precision NOT NULL,

    CONSTRAINT pk_budgets PRIMARY KEY(id)
);


 COMMENT ON TABLE budgets IS 'This table provide basic informations about the expenses.';
 COMMENT ON COLUMN budgets.id IS 'Column responsible about informations about expenses`s ID.';
 COMMENT ON COLUMN budgets.code IS 'Column responsible about informations about expense`s code, or external ID.';
 COMMENT ON COLUMN budgets.budget_name IS 'Column responsible about model informations of expenses.';
 COMMENT ON COLUMN budgets.budget_limit IS 'Column responsible about informations of maker of expenses.';
 COMMENT ON CONSTRAINT pk_budgets ON budgets IS 'Constraint responsible to guarantee the unike information on primary key of expense registry.';

ALTER TABLE IF EXISTS budgets OWNER to "user-finApp-api-java";

 CREATE INDEX IF NOT EXISTS ix_budgets_id
     ON budgets USING btree
     (id ASC NULLS LAST)
     TABLESPACE pg_default;
