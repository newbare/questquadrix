insert into "ROOT"."BANCA" ("ID_BANCA", "NOME_BANCA") values(1, 'Cespe')
insert into "ROOT"."BANCA" ("ID_BANCA", "NOME_BANCA") values(2, 'FGV')
insert into "ROOT"."BANCA" ("ID_BANCA", "NOME_BANCA") values(3, 'Quadrix')
insert into "ROOT"."INSTITUICAO" ("ID_INSTITUICAO", "NOME_INSTITUICAO") values(1, 'Banco do Brasil')
insert into "ROOT"."INSTITUICAO" ("ID_INSTITUICAO", "NOME_INSTITUICAO") values(2, 'Infraero')
insert into "ROOT"."INSTITUICAO" ("ID_INSTITUICAO", "NOME_INSTITUICAO") values(3, 'BB Tecnologia')
insert into "ROOT"."INSTITUICAO" ("ID_INSTITUICAO", "NOME_INSTITUICAO") values(4, 'TCU')
update "ROOT"."GRAU" set "TXT_NIVEL"='Superior' where "ID_GRAU"=1
insert into "ROOT"."GRAU" ("ID_GRAU", "TXT_NIVEL") values(2, 'Médio')
insert into "ROOT"."PROVA" ("ID_PROVA", "NOME", "ID_BANCA", "ID_GRAU", "ID_INSTITUICAO") values(1, 'PROVA F', 1, 2, 1)
insert into "ROOT"."PROVA" ("ID_PROVA", "NOME", "ID_BANCA", "ID_GRAU", "ID_INSTITUICAO") values(2, 'ANALISTA JAVA', 2, 2, 1)


