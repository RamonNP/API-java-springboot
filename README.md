# beach-product-rental

## Esse é um projecto Spring Boot para ser usado como base para o teste a seguir:
O objetivo principal do teste é verificar o algorítimo criado pelo candidato para calcular o valor a ser pago para o funcionário.

## Cenário: A empresa XYZ trabalha com aluguel de produtos que são utilizados na praia.

1. cada produto possui um valor cobrado por hora, o sistema aceita apenas valores inteiros para o campo hora;
1. funcionário recebe uma comisão baseada no valor total cobrado pelo produto.

## Produtos e comissões:
Para SURFBOARD:
Preço por hora: R$50,00
Porcentagem funcionário: 15.6%

Para BEACH_CHAIR:
Preço por hora: R$35,00
Porcentagem funcionário: 5%

Para SUNSHADE:
Preço por hora: R$40,00
Porcentagem funcionário: 10.3%

Para SAND_BOARD:
Preço por hora: R$25,00
Porcentagem funcionário: 9%

Para BEACH_TABLE:
Preço por hora: R$25,00
Porcentagem funcionário: 8.1%

## Descrição dos campos da classe ProductOrder.
1. userName: Login do usuário;
1. productType: Tipo do produto;
1. timeHour: Tempo em horas que o produto ficou locado;
1. productValue: Valor do produto por hora;
1. productTotal: Valor do produto vezes tempo em horas locado;
1. userAmount: Comissão a ser recebida pelo usuário(Funcionário);

## Exemplo:
Funcionário Pedro alugou o produto SURFBOARD por 4h.
Pedro vai receber uma comissão no valor de R$31,20 pelo aluguel do produto SURFBOARD.

# ATENÇÃO:
- O valor do produto, total do produto e o valor a ser pago para o funcionário devem ser salvos no banco de dados com o valor multiplicado por 100. Para o exemplo do Pedro, o valor da comissão a ser salva no banco de dados é de 3120.
- As classes ProductOrderDTO, ProductOrder e o enum ProductType não podem ser alteradas.
- Não é permitido criar novas entidades.
- Fique a vontade para criar novas classes.

Adicione na classe ProductOrderController um método para trazer a lista de pedidos, deve existir pelo menos um campo para ser utilizado como filtro.

Fique a vontade para adicionar mais funcionalidades ao teste, como: criação de testes unitários, utilização de docker etc.


# Boa sorte!!!
