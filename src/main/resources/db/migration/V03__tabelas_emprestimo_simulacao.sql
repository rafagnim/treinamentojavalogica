create table tb_emprestimo (
id integer unsigned auto_increment primary key not null,
cpf_cnpj varchar(14) not null,
valor decimal(15,2) not null,
parcelas integer not null,
data_nascimento date not null,
carencia varchar(10) not null,
taxa_mensal decimal(15,6) not null,
taxa_anual varchar(15) not null);

create index idx_simulacao on tb_emprestimo (cpf_cnpj);

create table tb_parcela (
id integer unsigned auto_increment primary key not null,
parcela integer not null,
data date not null,
amortizacao decimal(15,2),
juros decimal(15,2),
total_parcela decimal(15,2),
saldo_devedor decimal(15,2) not null,
emprestimo_id integer unsigned,
foreign key (emprestimo_id) references tb_emprestimo(id) );
