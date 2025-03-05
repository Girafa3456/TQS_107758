3.1

# (a)

* Exemplos:
    fonte: A_EmployeeRepositoryTest.java
    código:
        assertThat(found).isNotNull()
                 .extracting(Employee::getName)
                 .isEqualTo(persistedAlex.getName());

    fonte: E_EmployeeRestControllerTemplateIT.java 
    código:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");

# (b)

* As das anotações transitivas incluídas no @DataJpaTest são:
    @Transactional -> executado dentro de uma transação que reverte após a sua execução

    @AutoConfigureTestDatabase -> configura a base de dados em memória by default (ex: H2)

    @ExtendWith(SpringExtension.class) -> integração com JUnit 5

    @AutoConfigureDataJpa -> configura automaticamente os components do Spring Data JPA

# (c)

* No ficheiro B_EmployeeService_UnitTest.java o repositorio é simulado evitando a integração com a BD utilizando o mockito:
@Mock
private EmployeeRepository employeeRepository;

...

Mockito.when(employeeRepository.findByName("john")).thenReturn(john);
Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);

# (d)

* A diferença entre as anotações @Mock e @MockBean é que a primeira cria um mock sem contexto Spring enquanto que a outra é usada dentro do contexto tal como que o @Mock é mais usado em testes unitários enquanto que o @MockBean é usado para testes Spring Boot onde um bean precisa ser injetado.

# (e)

* O papel do file 'application-integrationtest.properties' serve para definir configurações especifícas para os testes para que se garanta que a sua configuração não interfere com a configuração principal da aplicação. E um exemplo de onde pode ser usado é quando se pretenda utilizar uma base de dados real em vez de in-memory ou se se desejar alterar parâmetros de testes como logging, configuração de APi, etc.

# (f)

Estratégia C -> Testa apenas a camada do controller, usando @WebMvcTest para carregar o controller e simular a camada do service.

Estratégia D -> Testa várias camada (controller, service e repository) usando @SpringBootTest para interagir com uma base de dados de teste.

Estratégia E -> Testa o fluxo todo da API com pedidos HTTP reais usando @SpingBootTest com TestRestTemplate real.