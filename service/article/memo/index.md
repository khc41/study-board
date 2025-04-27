### 쿼리 인덱스 추가하기

~~~mysql
select * from article where board_id = 1 order by created_at desc limit 30 offset 90
~~~
페이징 조회를 하는 단순한 쿼리라도 인덱스를 타지 않으면 애플리케이션에서 사용할 수 없을 만큼 느려지게 된다.

![img.png](images/img.png)

이 문제를 해결하기 위해 인덱스를 추가 해준다.

~~~mysql
create index idx_board_id_article_id on article(board_id asc, article_id desc);
~~~

![img_1.png](images/img_1.png)

### 추가 문제: Offset이 커질 때
~~~mysql
select * from article where board_id = 1 order by article_id desc limit 30 offset 1499970;
~~~
![img_2.png](images/img_2.png)

하지만, offset이 커지게 되면 다시 느려지게 된다. 
이는, secondary index가 현재 article_id, board_id 만 포함하기 때문에 데이터를 가져오기 위해 다시 clustered index를 조회해야 하기 때문이다.


이를 해결하기 위해 offset 구간까지는 secondary index만 사용하고, 필요한 레코드만 clustered index를 조회하는 방식으로 쿼리를 수정할 수 있다. 
~~~mysql
select * from ( select article_id from article where board_id = 1 order by article_id desc limit 30 offset 1499970 ) t left join article on t.article_id = article.article_id;
~~~
![img_3.png](images/img_3.png)

### 추가 고려사항
offset이 너무 커지면 이 방법도 한계가 있다.
연단위로 테이블을 분리하거나 정책적으로 최신 글 위주로 페이징 하도록 유도하는 등으로 해결할 수 있다.
