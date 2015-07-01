select
        prova0_.ID_PROVA as ID_PROVA1_5_,
        banca2_.nome_banca as ID_BANCA3_5_,
        grau1_.txt_Nivel as ID_GRAU4_5_,
        instituica3_.nome_INSTITUICAO as ID_INSTI5_5_,
        prova0_.NOME as NOME2_5_ 
    from
        ROOT.PROVA prova0_ 
    inner join
        ROOT.GRAU grau1_ 
            on prova0_.ID_GRAU=grau1_.ID_GRAU 
    inner join
        ROOT.BANCA banca2_ 
            on prova0_.ID_BANCA=banca2_.ID_BANCA 
    inner join
        ROOT.INSTITUICAO instituica3_ 
            on prova0_.ID_INSTITUICAO=instituica3_.ID_INSTITUICAO 
    order by
        prova0_.ID_PROVA ASC