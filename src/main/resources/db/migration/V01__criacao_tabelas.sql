create table tb_contrato (
id integer unsigned auto_increment primary key not null,
cpf_cnpj varchar(14) not null,
vl_contrato decimal(15,2) not null);

create index idx_contrato on tb_contrato (cpf_cnpj);

create table tb_contrato_item (
id integer unsigned auto_increment primary key not null,
id_duplicata varchar(30) not null,
vl_duplicata decimal(12,2) not null,
contrato_id integer unsigned not null,
foreign key (contrato_id) references tb_contrato(id) );

create index idx_contrato_item on tb_contrato_item (id_duplicata, vl_duplicata);

create table tb_valida_cpf_cnpj (
cpf_cnpj varchar(14) primary key unique not null,
create_date date not null,
update_date date not null,
result int not null );

create index idx_valida_cpf_cnpj on tb_valida_cpf_cnpj (create_date, update_date);