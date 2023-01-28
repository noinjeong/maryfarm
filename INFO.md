## Monolithic

1. @RequestBody를 String으로 받으면 Json처리 안됨.
2. ResposneDTO에 Entity 들어가면 프록시 객체일 수도 있으므로 왠만하면 안넣는게 좋을듯함.
3. INNER JOIN, LEFT OUTER JOIN, RIGHT OUTER JOIN이 존재하고, 일대다 상태에서 INNER JOIN을 해도 문제없음. 데이터 중복은 LEFT JOIN에서 발생할 확률이 크므로 조심.
4. 일대다 상태에서 FETCH JOIN을 하면 무조건, 뻥튀기 문제가 발생하게 됨. 따라서 DISTINCT가 요구됨.
5. OneToOne에서 주인이 아닌 쪽은 LazyLoading이 동작하지 않는데, OneToMany에서 주인이 아닌 쪽에서는 제대로 동작함.
6. 연관관계 Entity를 가지고 있을 때 주인이라면 DB상에서 FK를 가지고 있는 꼴임. 근데, JPA상에서는 객체이므로 Lazy Loading을 하면 프록시 객체를 가지게 되는데, 해당 프록시 객체에서 Id는 Getter로 조회해도 프록시 초기화가 발생하지 않음. 당연하게 DB상에서는 FK를 가지고 있으니까 그러는 듯함.
7. 프론트에 넘겨줄 DTO에 프록시 객체가 껴있지 않도록 주의해야 함. 아니면 프록시 객체 출력 예외 발생함.
8. 양방향 연관관계시 프론트에 json으로 넘겨주려면 한쪽에 @JsonIgnore을 붙여줘야 함.
9. Hibernate5Module를 등록해주면 프록시 객체를 json으로 변환시 예외가 발생하지 않고, 초기화되지 않은 프록시 객체는 출력되지 않고, 초기화된 프록시 객체만 출력됨. 다만, 이렇게 Entity를 직접 노출시키는 것보다 DTO로 만들어주는게 더 바람직함.
10. DB에서 search 속도 최적화를 위해 사용되는 INDEX는 search를 원하는 특정 칼럼에 대해 INDEX만 생성하면 이후의 조회에 적용됨. INDEX 사용시 explain을 해서 INDEX를 타는지 확인해주는 게 좋음.
11. 로그인, 검색 부분에서 INDEX를 사용해주는 게 좋을 듯함.
12. Entity타입 Collection은 왠만하면 다대다 관계일 것이므로 중간에 다대다 전용 테이블을 설정하고, 값타입 Collection은 값타입을 Entity로 묶어서 따로 테이블로 만들어주는 게 좋음.
13. @EmbeddedId와 @MapsId의 조합으로 외래키1개+일반컬럼1개의 복합키를 만들 수 있음.
14. Entity의 속성값들은 전부 값타입으로 줘야 함.
15. mappedBy를 사용해 양방향 연관관계를 설정하는 이유는 다른 테이블과 join한 결과를 해당 Entity로 가져올 때 다른 테이블의 컬럼값들을 담아오기 위해서임. 그렇게 안하면 join한 테이블들의 컬럼값들을 가져올 수 없음.
16. 가져오는 Entity 내의 연관관계 Entity 내의 연관관계 Entity를 프록시 객체로 가져오지 않으려면 Fetch join을 하나의 쿼리안에 여러번 실행해서 해결할 수 있음.
17. 팔로우한 유저의 일지 중 가장 최신 날짜를 조회한다던가, 작물id로 조회된 일지들 중 가장 최신 날짜를 조회하는 쿼리문은 서브쿼리를 사용해줘야 함. 근데 jpql에서 서브쿼리 사용하는거 빡세서 querydsl공부하고 해야할거 같음.
18. repository의 save 메서드를 통해 반환되는 Entity는 연관관계 Entity들이 전부 진짜로 채워져서 나옴. find는 프록시 객체로 나오는 것과 다름.
19. entity의 dirty check를 통해 변경시키는 게 update 쿼리를 짜는것보다 간단할 듯함.

## MSA

1. FeignClient에서 RequestDTO를 사용하려면 사용하려는 곳에서 요청하려는 곳의 RequestDTO의 클래스를 가지고 있어야 함. 예를 들어 Member에서 Order의 메서드를 FeignClient로 실행하려면 OrderRequestDTO를 가지고 있어야 함. 이는 실무에서 라이브러리를 공유함으로써 해결할 수 있음.
2. Swagger API DOCS를 보면서 Postman으로 데이터를 보내주는 게 엄청 편함.
3. FeignClient를 사용하는 곳은 Service, Controller 모두 가능할 듯함.
