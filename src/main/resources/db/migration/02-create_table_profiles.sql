 --liquibase formatted sql

--changeset dsw:2
CREATE TABLE IF NOT EXISTS profiles (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    code character varying(36) NOT NULL,
    name character varying(100) NOT NULL,
    CONSTRAINT pk_profiles PRIMARY KEY(id),
    CONSTRAINT profiles_uq_name UNIQUE (name)
 );

 CREATE TABLE IF NOT EXISTS users_profiles (
     user_id integer NOT NULL,
     profile_id integer NOT NULL,
     CONSTRAINT user_id_fk_user FOREIGN KEY (user_id) REFERENCES users (id),
     CONSTRAINT profile_id_fk_profile FOREIGN KEY (profile_id) REFERENCES profiles (id)
  );

COMMENT ON TABLE profiles IS 'TThis table provides basic information about profiles.';
COMMENT ON COLUMN profiles.id IS 'Column responsible for profile ID information.';
COMMENT ON COLUMN profiles.code IS 'Column responsible for information about the profile code, or external ID.';
COMMENT ON COLUMN profiles.name IS 'Column responsible for profile name information.';
COMMENT ON CONSTRAINT pk_profiles ON profiles IS 'Constraint responsible for guaranteeing unique information in the primary key of the profile registration.';
COMMENT ON CONSTRAINT profiles_uq_name ON profiles IS 'Constraint responsible for ensuring unique information in the profile`s name registration.';

COMMENT ON TABLE users_profiles IS 'This table provides basic information about the relationship between profiles and users.';
COMMENT ON COLUMN users_profiles.user_id IS 'Column responsible for user identification information.';
COMMENT ON COLUMN users_profiles.profile_id IS 'Column responsible for profile identification information.';
COMMENT ON CONSTRAINT user_id_fk_user ON users_profiles IS 'Constraint refer the foreign key relationship to users table.';
COMMENT ON CONSTRAINT profile_id_fk_profile ON users_profiles IS 'Constraint refer the foreign key relationship to profiles table.';

ALTER TABLE IF EXISTS profiles OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS users_profiles OWNER to "user-finApp-api-java";

CREATE INDEX IF NOT EXISTS ix_profiles_id
    ON profiles USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_profiles_id
    ON users_profiles USING btree
    (user_id ASC NULLS LAST)
    TABLESPACE pg_default;