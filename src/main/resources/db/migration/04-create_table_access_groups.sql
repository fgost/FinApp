--liquibase formatted sql

--changeset dsw:4
CREATE TABLE IF NOT EXISTS access_groups (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    code character varying(36) not null,
    name character varying(100) NOT NULL,
    CONSTRAINT access_groups_pk PRIMARY KEY(id),
    CONSTRAINT access_groups_uq_name UNIQUE (name)
 );

CREATE TABLE IF NOT EXISTS access_groups_permissions (
    access_group_id integer NOT NULL,
    permission_key character varying(100) NOT NULL,
    permission_value character varying(100) NOT NULL,
    CONSTRAINT fk_profile FOREIGN KEY(access_group_id) REFERENCES access_groups (id)
);

CREATE TABLE IF NOT EXISTS access_groups_users (
    user_id integer NOT NULL,
    access_group_id integer NOT NULL,
    CONSTRAINT user_id_fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT access_group_id_fk_user FOREIGN KEY (access_group_id) REFERENCES access_groups (id)
 );

 CREATE TABLE IF NOT EXISTS profiles_access_groups (
     profile_id integer NOT NULL,
     access_group_id integer NOT NULL,
     CONSTRAINT profile_id_fk_profile FOREIGN KEY (profile_id) REFERENCES profiles (id),
     CONSTRAINT access_group_id_fk_access_group FOREIGN KEY (access_group_id) REFERENCES access_groups (id)
  );

COMMENT ON TABLE access_groups IS 'This table provides basic information about access groups.';
COMMENT ON COLUMN access_groups.id IS 'Column responsible for access group identification information.';
COMMENT ON COLUMN access_groups.code IS 'Column responsible for information about the access group code, or external ID.';
COMMENT ON COLUMN access_groups.name IS 'Column responsible for access group name information.';
COMMENT ON CONSTRAINT access_groups_pk ON access_groups IS 'Constraint responsible for guaranteeing unique information in the primary key of the access group registration.';
COMMENT ON CONSTRAINT access_groups_uq_name ON access_groups IS 'Constraint responsible for ensuring unique information in the access group name registration.';

COMMENT ON TABLE access_groups_permissions IS 'This table provides basic information about permissions of the access groups.';
COMMENT ON COLUMN access_groups_permissions.access_group_id IS 'Column responsible for access group identification information.';
COMMENT ON COLUMN access_groups_permissions.permission_key IS 'Column responsible for information about the permission key.';
COMMENT ON COLUMN access_groups_permissions.permission_value IS 'Column responsible for permission value information.';
COMMENT ON CONSTRAINT fk_profile ON access_groups_permissions IS 'Constraint refer the foreign key relationship to access group table.';

COMMENT ON TABLE access_groups_users IS 'This table provides basic information about the relationship between access group and users.';
COMMENT ON COLUMN access_groups_users.user_id IS 'Column responsible for user identification information.';
COMMENT ON COLUMN access_groups_users.access_group_id IS 'Column responsible for access group identification information.';
COMMENT ON CONSTRAINT user_id_fk_user ON access_groups_users IS 'Constraint refer the foreign key relationship to user table.';
COMMENT ON CONSTRAINT access_group_id_fk_user ON access_groups_users IS 'Constraint refer the foreign key relationship to access group table.';

COMMENT ON TABLE profiles_access_groups IS 'This table provides basic information about the relationship between profiles and access group.';
COMMENT ON COLUMN profiles_access_groups.profile_id IS 'Column responsible for profile identification information.';
COMMENT ON COLUMN profiles_access_groups.access_group_id IS 'Column responsible for access group identification information.';
COMMENT ON CONSTRAINT profile_id_fk_profile ON profiles_access_groups IS 'Constraint refer the foreign key relationship to profile table.';
COMMENT ON CONSTRAINT access_group_id_fk_access_group ON profiles_access_groups IS 'Constraint refer the foreign key relationship to access group table.';

ALTER TABLE IF EXISTS access_groups OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS access_groups_permissions OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS access_groups_users OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS profiles_access_groups OWNER to "user-finApp-api-java";

CREATE INDEX IF NOT EXISTS ix_access_groups_id
    ON access_groups USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_access_groups_permissions_id
    ON access_groups_permissions USING btree
    (access_group_id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_access_groups_users_id
    ON access_groups_users USING btree
    (user_id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_profiles_access_groups_id
    ON profiles_access_groups USING btree
    (profile_id ASC NULLS LAST)
    TABLESPACE pg_default;
