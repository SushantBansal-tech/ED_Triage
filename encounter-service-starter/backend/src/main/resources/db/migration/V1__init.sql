create table encounters (
  id bigserial primary key,
  encounter_number varchar(64) not null unique,
  patient_id varchar(64) not null,
  patient_name varchar(255) not null,
  status varchar(32) not null,
  encounter_class varchar(32) not null,
  priority varchar(32) not null,
  location varchar(64) not null,
  reason_for_visit text not null,
  attending_practitioner varchar(255),
  started_at timestamp,
  ended_at timestamp,
  created_at timestamp not null,
  updated_at timestamp not null
);
