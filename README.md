<h2 style="text-align: center"><center>당신을 웃음짓게 하는 아름다운 길 추천 서비스</center> </h2>
<h1 style="text-align: center"><center>GILL</center></h1>
<img src="https://cphoto.asiae.co.kr/listimglink/1/2022040707381119271_1649284690.jpg" />
<details>
<summary style="font-size: 18px; font-weight: bold">프로젝트 개요</summary>
<div markdown="1">

저는 산책하는 것을 좋아하고, 새로운 예쁜 길을 걷는 경험을 즐깁니다. 하지만 원하는 길을 쉽게 찾을 수 있는 서비스가 부족하다는 점을 느꼈습니다.

이에 따라 공공데이터를 활용하여 우리나라의 아름다운 길 정보를 제공하고, 사용자에게 맞는 길을 추천하며, 자신만의 길을 공유할 수 있는 애플리케이션을 만들고자 합니다.

이 프로젝트는 다양한 길 정보를 제공하고, 사용자가 직접 경험한 길을 공유하며 함께 즐길 수 있는 커뮤니티 기반의 서비스로 발전하는 것을 목표로 합니다.


</div>
</details>

</br>

<details>
<summary style="font-size: 18px; font-weight: bold">프로젝트 설계 및 디렉토리 구조</summary>
<div markdown="2">

<h3>디렉토리 구조 (MVC 패턴 + 계층형 아키텍처 적용)</h3>

📁 src</br>
├── 📁 com.pathfinder.domain      # 도메인 모델 (User, Path)</br>
├── 📁 com.pathfinder.service     # 비즈니스 로직 (UserService, PathService)</br>
├── 📁 com.pathfinder.persistence # 데이터 저장소 (UserRepository, PathRepository)</br>
├── 📁 com.pathfinder.utils       # 유틸리티 클래스 (JsonParser, JsonWriter)</br>
├── 📁 com.pathfinder.ui          # 사용자 인터페이스 (Application.java)

<h3>각 계층의 역할</h3>

**📁 persistence (데이터 관리 계층)**

- 길(Path) 및 사용자(User) 데이터를 관리합니다.

- JSON 파일을 읽고, 데이터를 저장, 수정, 삭제하는 역할을 수행합니다.

- 향후 DB 저장소(DBRepository)로 확장 가능하도록 인터페이스 분리 예정

**📁 service (비즈니스 로직 계층)**

- UI와 데이터 저장소를 연결하는 역할을 합니다.

- 사용자의 입력을 검증하고, 비즈니스 로직을 수행합니다.

- 예를 들어, 맞춤형 길 추천 기능, 회원가입 시 유효성 검사 등의 로직을 처리합니다.

**📁 utils (유틸리티 계층)**

- JsonParser: JSON 데이터를 파싱하는 역할을 수행합니다. (데이터 캐싱 X, 경로 기반으로 JSON을 파싱하여 List<T>로 반환)

- JsonWriter: JSON 데이터를 저장하는 역할을 수행합니다. (JsonParser와 분리하여 SRP 원칙 준수)

**📁 ui (사용자 인터페이스 계층)**

- 콘솔 기반의 UI를 제공하며, 사용자의 입력을 처리합니다.

- 로그인 및 회원가입, 길 검색 등의 메뉴를 관리합니다.

<h3>계층형 아키텍처 구조를 사용한 이유와 장점</h3>

- 유지보수성 증가 → 각 계층의 역할이 명확하여 변경이 용이

- 확장성 고려 → 추후 데이터 저장소를 DB로 변경할 경우 최소한의 수정으로 대응 가능

- 책임 분리 → 단일 책임 원칙(SRP)을 준수하여 코드의 가독성과 테스트 용이성 향상
</div>
</details>
</br>
<details>
<summary style="font-size: 18px; font-weight: bold">주요 기능 소개</summary>
<div markdown="3">

<h3>1. 페이징된 길 목록 조회</h3>
   길 데이터는 많기 때문에 한 번에 모든 데이터를 불러오면 성능 저하가 발생할 수 있습니다. 따라서 한 페이지당 20개씩 조회하는 페이징 기능을 적용했습니다.

**✅ 페이징 기능 구현 과정**

- `PathRepository`에서 모든 길 목록을 로드하는 기존 방식에서 페이지 단위로 데이터를 불러오는 방식으로 변경
- `getPathsByPage(int pageNumber)` 메서드를 추가하여 요청한 페이지 번호에 해당하는 길 목록만 반환하도록 구현
- `Application`에서 사용자가 "다음 페이지"를 요청하면 pageNumber를 증가시켜 추가 데이터를 동적으로 로드

<h3>2. 특정 거리 이하의 길 검색</h3>

사용자가 원하는 거리 이내의 길만 조회할 수 있도록 필터링 기능을 추가했습니다.

**✅ 기능 동작 방식**
- 사용자가 "10km 이하의 길을 보고 싶어요!"라고 입력하면
`PathService`에서 길 목록을 필터링하여 10km 이하인 길만 반환
`Application`에서 필터링된 길 목록을 출력
  ```
  예시 실행 결과 :  
  원하는 길의 최대 거리를 입력하세요: 10
   8.5km - 한강 공원 산책로
   9.2km - 남산 둘레길 
  ```

배운 점:

- List<Path>에서 특정 조건(distance <= 사용자 입력 값)에 맞는 데이터만 필터링하는 방법을 익혔습니다.
- 데이터를 필터링할 때, 입력값을 double로 변환하는 과정에서 발생할 수 있는 예외 처리가 필요하다는 점을 알게 되었습니다.

<h3>3. 맞춤형 길 추천 기능</h3>

사용자가 원하는 조건(거리, 소요 시간, 지역 등)을 입력하면 가장 적합한 길을 추천하는 기능입니다.

✅ 추천 로직
1. 사용자 입력 값 수집
- "최대 거리", "최대 소요 시간", "출발지" 등을 입력받음
2. 입력값에 맞춰 필터링
- PathService에서 사용자의 조건과 가장 일치하는 길을 탐색
3. 가장 적절한 길 추천(구현 예정)
- 최적의 길이 여러 개라면 가장 인기 있는 길(좋아요 수 기준)을 반환

</div>
</details>


