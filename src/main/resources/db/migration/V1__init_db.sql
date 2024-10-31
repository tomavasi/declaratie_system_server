DROP SEQUENCE IF EXISTS "medewerker_id_seq";
DROP SEQUENCE IF EXISTS "declaratie_id_seq";
CREATE SEQUENCE "medewerker_id_seq" INCREMENT BY 50 START 1;
CREATE SEQUENCE "declaratie_id_seq" INCREMENT BY 50 START 1;

DROP TABLE IF EXISTS "medewerkers";
CREATE TABLE "medewerkers" (
   "id" bigint NOT NULL,
   "name" VARCHAR(512),
   "bankrekening" VARCHAR(512),
   "provider_id" bigint,
   CONSTRAINT "medewerker_pkey" PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "declaraties";
CREATE TABLE "declaraties" (
   "id" bigint NOT NULL,
   "aankoop_org" VARCHAR(512),
   "aankoop_datum" VARCHAR(512),
   "bedrag" decimal,
   "datum" VARCHAR(512),
   "medewerker_id" bigint,
   CONSTRAINT "declaratie_pkey" PRIMARY KEY ("id")
);