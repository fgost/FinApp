 --liquibase formatted sql

--changeset dsw:3
CREATE TABLE IF NOT EXISTS revinfo (
    rev integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    revtstmp bigint DEFAULT NULL,
    user_id integer NOT NULL,
    CONSTRAINT revinfo_pk PRIMARY KEY (rev)
 );

CREATE TABLE IF NOT EXISTS users_audit (
	id integer NOT NULL,
	rev integer NOT NULL,
	revtype int2 NULL,
	code varchar(255) NULL,
	email varchar(255) NULL,
	last_name varchar(255) NULL,
	name varchar(255) NULL,
	password varchar(255) NULL,
	CONSTRAINT users_audit_pk PRIMARY KEY (rev, id),
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS profiles_audit (
	id integer NOT NULL,
	rev integer NOT NULL,
	revtype int2 NULL,
	code varchar(255) NULL,
	name varchar(255) NULL,
	CONSTRAINT profiles_audit_pk PRIMARY KEY (rev, id),
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS access_groups_audit (
    id integer NOT NULL,
	rev integer NOT NULL,
	revtype int2 NULL,
	code varchar(255) NULL,
	name varchar(255) NULL,
    CONSTRAINT access_groups_audit_pk PRIMARY KEY (rev, id),
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS access_groups_permissions_aud (
	access_group_id integer NOT NULL,
	rev integer NOT NULL,
	revtype int2 NULL,
	setordinal integer NOT NULL,
	permission_key varchar(255) NULL,
	permission_value varchar(255) NULL,
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS users_preferences_aud (
	user_id integer NOT NULL,
	rev integer NOT NULL,
	revtype int2 NULL,
	setordinal integer NOT NULL,
	preference_key varchar(255) NULL,
	preference_value varchar(255) NULL,
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS users_profiles_aud (
	rev integer NOT NULL,
	user_id integer NOT NULL,
	profile_id integer NOT NULL,
	revtype int2 NULL,
	CONSTRAINT users_profiles_aud_pk PRIMARY KEY (rev, user_id, profile_id),
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS profiles_access_groups_aud (
	rev integer NOT NULL,
	access_group_id integer NOT NULL,
	profile_id integer NOT NULL,
	revtype int2 NULL,
	CONSTRAINT profiles_access_groups_aud_pk PRIMARY KEY (rev, access_group_id, profile_id),
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS users_access_groups_aud (
	rev integer NOT NULL,
	access_group_id integer NOT NULL,
	user_id integer NOT NULL,
	revtype int2 NULL,
	CONSTRAINT users_access_groups_aud_pk PRIMARY KEY (rev, access_group_id, user_id),
	CONSTRAINT fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

CREATE TABLE IF NOT EXISTS user_photo_audit (
    user_id integer NOT NULL,
    file_name varchar(255) NULL,
    url varchar(255) NULL,
    size varchar(255) NULL,
    content_type varchar(255) null,
    rev integer NOT NULL,
	revtype int2 NULL,
	CONSTRAINT user_photo_audit_pk PRIMARY KEY (rev, user_id),
	CONSTRAINT user_photo_fk_rev FOREIGN KEY (rev) REFERENCES revinfo(rev)
);

ALTER TABLE IF EXISTS revinfo OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS users_audit OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS profiles_audit OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS profiles_permissions_aud OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS users_profiles_aud OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS profiles_access_groups_aud OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS users_access_groups_aud OWNER to "user-finApp-api-java";
ALTER TABLE IF EXISTS user_photo_audit OWNER to "user-finApp-api-java";

CREATE INDEX IF NOT EXISTS ix_revinfo_rev
    ON revinfo USING btree
    (rev ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_users_audit_id
    ON users_audit USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_profiles_audit_id
    ON profiles_audit USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_users_profiles_aud_rev
    ON users_profiles_aud USING btree
    (rev ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_users_access_groups_aud_rev
    ON users_access_groups_aud USING btree
    (rev ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_user_photo_audit_user_id
    ON user_photo_audit USING btree
    (user_id ASC NULLS LAST)
    TABLESPACE pg_default;
