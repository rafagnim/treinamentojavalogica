
create table tb_parametro(
id Integer unsigned auto_increment primary key not null,
description varchar(100) not null,
vl_parameter varchar(50) not null);

create index idx_emprestimo on tb_parametro (description);

insert into tb_parametro values (null, "TAX_PATTERN", "12");