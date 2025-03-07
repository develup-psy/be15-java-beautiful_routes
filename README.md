<div align="center">

### 🏞️ 당신을 위한 아름다운 길 추천 서비스 🏞️

# GILL 

<img src="https://cphoto.asiae.co.kr/listimglink/1/2022040707381119271_1649284690.jpg" />

</div>
</br>
저는 산책하는 것을 좋아하고, 새로운 예쁜 길을 걷는 경험을 즐깁니다. 하지만 원하는 길을 쉽게 찾을 수 있는 서비스가 부족하다는 점을 느꼈습니다.


이에 따라 <a href="https://www.data.go.kr/tcs/dss/selectDataSetList.do">공공데이터</a>
를 활용하여 우리나라의 아름다운 길 정보를 제공하고, 사용자에게 맞는 길을 추천하며, 자신만의 길을 공유할 수 있는 애플리케이션을 만들고자 합니다.

이 프로젝트는 다양한 길 정보를 제공하고, 사용자가 직접 경험한 길을 공유하며 함께 즐길 수 있는 커뮤니티 기반의 서비스로 발전하는 것을 목표로 하고자 합니다.

</br>
<details>
<summary><span style="font-size: 18px; font-weight: bold">📌 프로젝트 설계 및 디렉토리 구조</span></summary>
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
<summary><span style="font-size: 18px; font-weight: bold">📌 주요기능 소개</span></summary>
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
</br>
<details>
<summary><span style="font-size: 18px; font-weight: bold">📌 설계 과정(OOP 및 SOLID 원칙)</span></summary>
<div markdown="4">

<h3>1. 객체지향 설계(OOP) 적용</h3>

본 프로젝트에서는 객체 지향의 4대 원칙(캡슐화, 상속, 다형성, 추상화)을 적용하여 유지보수성과 확장성을 높이고자 노력했습니다.
- **캡슐화 (Encapsulation)**
  - User, Path 객체의 필드를 private으로 선언하고, getter/setter를 통해 데이터 접근을 제어함으로써 불필요한 외부 접근을 차단하였습니다.
  - 예를 들어, User 클래스에서 비밀번호(password) 필드는 private으로 설정하여 직접 수정할 수 없도록 하고, 비밀번호 검증 메서드를 통해 변경하도록 설계하였습니다.
- **다형성 (Polymorphism)**
  - JsonParser는 제네릭(Generic)을 활용하여 다양한 타입의 데이터를 처리할 수 있도록 구현하였습니다.
    - parse(String filePath, Class<T> clazz) 메서드를 통해 어떤 JSON 데이터든 특정 타입으로 변환 가능하도록 설계되었습니다.
  - Repository 인터페이스를 사용하지 않고 개별적으로 PathRepository와 UserRepository를 설계하였지만, 향후 인터페이스를 활용한 다형성을 추가적으로 적용할 계획입니다.
- **상속 (Inheritance)**
  - 현재 프로젝트에서는 상속을 활용하지 않았지만, 추후 DataRepository<T> 인터페이스를 정의하여 PathRepository, UserRepository가 공통된 기능을 상속받도록 개선할 계획입니다.
  - 이를 통해 데이터 저장소를 DB로 변경할 경우에도 최소한의 수정으로 확장할 수 있도록 설계할 예정입니다. 
- **추상화 (Abstraction)**
  - 추후에 DataRepository<T> 인터페이스를 만들어 PathRepository, UserRepository에서 데이터 저장과 조회의 공통 기능을 추상화할 예정입니다. 
  - 이는 다른 DB 저장소로 확장할 경우 DBRepository를 새롭게 추가하여 기존 코드 수정 없이 연동 가능하도록 설계하고자 함입니다.

<h3>SOLID 원칙 적용</h3>
본 프로젝트에서는 SOLID 원칙을 적용하여 유지보수성과 확장성을 높이는 구조를 설계하는 것을 목표로 했습니다. 
하지만 각 원칙을 적용하는 과정에서 다양한 시행착오가 있었으며, 이를 해결하면서 코드 구조를 개선하는 경험을 하게 되었습니다.

1. 단일 책임 원칙 (SRP) 적용
- JsonParser는 JSON 데이터 읽기(파싱) 전담
- JsonWriter는 JSON 데이터 저장 전담
- PathRepository, UserRepository는 데이터 조회 및 관리 전담

**문제상황**
- JsonParser가 데이터 저장까지 담당하고 있었음.
- 초기에는 JsonParser에서 JSON 데이터 읽기와 저장을 동시에 처리하고 있었습니다.
- 그런데 이렇게 되면, JSON 저장 방식이 변경되면 메서드를 수정해야 하는 문제가 발생하게 되고 유지보수가 좋지 않다는 것을 확인할 수 있었습니다.

SRP 위반코드)
```java
public class JsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> parse(String filePath, Class<T> clazz) {
        try {
            File file = new File(filePath);
            JsonNode rootNode = objectMapper.readTree(file);
            return objectMapper.readValue(rootNode.toString(), new TypeReference<List<T>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static <T> void save(String filePath, List<T> data) { // SRP 위반 (저장 기능 포함)
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**해결 과정**
- JsonWriter 클래스를 분리하여 저장 책임을 분리
- JsonParser는 JSON 읽기(파싱)만 담당하도록 유지
- JsonWriter를 별도로 생성하여 JSON 데이터 저장 역할을 분리

개선된 코드)
```java
public class JsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> parse(String filePath, Class<T> clazz) {
        try {
            File file = new File(filePath);
            JsonNode rootNode = objectMapper.readTree(file);
            return objectMapper.readValue(rootNode.toString(), new TypeReference<List<T>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

public class JsonWriter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void save(String filePath, List<T> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
결과적으로 책임이 명확해지고 이터 저장 방식이 바뀌어도 JsonParser를 수정할 필요가 없게 되었습니다. 
이를 통해 유지보수성이 증가하고 확장성이 개선되었습니다. 

<h3>개방-폐쇄 원칙 (OCP) 적용</h3>
- JsonParser를 제네릭(Generic) 적용하여 특정 JSON 구조에 종속되지 않도록 개선
- 새로운 데이터가 추가될 때 기존 코드 변경 없이 확장 가능하도록 개선

**문제 상황**
- JsonParser가 특정 JSON 구조(길 데이터)에 종속적이었음
- 처음 JsonParser는 routes.json(길 데이터)만 처리할 수 있도록 설계되었는데 users.json을 추가하려고 하니 기존 코드를 수정해야 했고 새로운 JSON 파일이 추가될 때마다 코드 수정이 필요하다는 문제가 발생했습니다.

**해결 과정**
- JsonParser에서 제네릭을 활용하여 특정 데이터 타입에 종속되지 않도록 변경하였습니다. 
- parse(String filePath, Class<T> clazz) 형태로 구현하여 파일 경로와 변환할 클래스 타입을 동적으로 지정할 수 있도록 개선하였습니다. 

이를 통해 JsonParser 수정 없이 어떤 JSON 파일(users.json, routes.json 등)도 처리 가능하게 되었고
새로운 JSON 데이터가 추가되어도 코드 수정이 필요하지 않게 되어 확장에는 열려 있고, 변경에는 닫힌 구조로 개선되었습니다. 

<h3>DIP (의존 역전 원칙) 적용</h3>
- 현재는 Service 계층이 JsonParser에 직접 의존하고 있는 상황입니다.
- 이는 데이터 저장 방식이 JSON에서 DB로 변경될 경우 Service 계층도 수정해야 합니다. 
- 따라서 추후에 Service 계층이 직접 JsonParser를 호출하는 것이 아니라 추상 인테페이스를 구현하여 구체적인 구현이 아닌 추상 인터페이스에 의존하도록 수정하고자 합니다. 

</div>
</details>

</br>

<details>
<summary><span style="font-size: 18px; font-weight: bold">📌트러블 슈팅 </span></summary>
</br>

이번 프로젝트를 진행하면서 다양한 문제를 마주했고, 이를 해결하는 과정을 통해 객체지향 설계의 중요성과 SOLID 원칙을 적용하는 실전 경험을 쌓을 수 있었습니다.
각 문제를 해결하는 과정에서 문제의 원인을 분석하고, 해결 방법을 탐색하며, 논리적인 접근법을 활용하여 개선하는 것을 목표로 했습니다.

<h3>1. JSON 데이터 파싱 중 UnrecognizedPropertyException 발생</h3>
**문제 상황** 
- JSON 필드가 예기치 않게 추가될 때 오류 발생
초기에는 JsonParser가 JSON 파일에서 특정 필드만 읽도록 구현되었습니다.
- 하지만, 공공데이터 API에서 추가적인 필드가 포함된 새로운 JSON 형식이 제공되면서
예상하지 못한 필드가 존재하면 Jackson 라이브러리가 이를 인식하지 못하고 UnrecognizedPropertyException을 발생시키는 문제가 발생했습니다.

**에러 메시지**:
> com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException:
Unrecognized field "길소개" (class com.pathfinder.domain.Path), not marked as ignorable

**문제 원인 분석**

Jackson 라이브러리는 기본적으로 클래스에 정의되지 않은 필드가 포함된 JSON을 역직렬화할 때 오류를 발생시킵니다.
Path 클래스에 "길소개" 필드가 존재하지 않지만, JSON 파일에는 해당 필드가 포함되어 있어 오류가 발생했습니다.

**해결 방법 탐색**

- Jackson에서는 정의되지 않은 필드를 무시하는 옵션을 제공합니다.
- @JsonIgnoreProperties(ignoreUnknown = true) 어노테이션을 클래스에 추가하면 정의되지 않은 필드가 있더라도 오류 없이 무시하도록 설정할 수 있습니다.

**적용 코드 (해결 후 코드)**
```
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 필드는 무시
public class Path {
private String name;
private double distance;
private String duration;
private String startPoint;
private String endPoint;
}
```
**해결 결과 및 개선점**
- 추가적인 필드가 JSON에 존재하더라도 오류가 발생하지 않음
- 공공데이터의 JSON 형식이 변경되어도 유지보수가 용이해짐
- 확장성을 고려한 JSON 처리 방식으로 개선

**추가 개선 방향**
- 현재는 ignoreUnknown = true 설정을 통해 해결했지만, 새로운 필드가 의미 있는 정보라면 이를 반영할 수 있는 구조도 함께 고려해야 함

<h3>2. JSON double 타입 변환 오류 (InvalidFormatException)</h3>

**문제 상황**
- double로 변환할 수 없는 문자열 포함
- "총길이" 값이 "11.8+13" 같은 올바르지 않은 숫자 형식으로 저장된 경우, 이를 double 타입으로 변환하려고 하면 InvalidFormatException이 발생하는 문제가 있었습니다.

**에러 메시지**
> com.fasterxml.jackson.databind.exc.InvalidFormatException:
Cannot deserialize value of type `double` from String "11.8+13":
not a valid `double` value


**문제 원인 분석**
- "11.8+13" 같은 데이터는 실제 거리 값이 "11.8km + 13km"라는 의미로 저장된 잘못된 입력값
Jackson은 이를 double로 변환할 수 없기 때문에 예외가 발생

**해결 방법 탐색**

- 총길이 필드를 String으로 저장한 후, 필요할 때 숫자로 변환하는 방식 적용
문자열에서 숫자만 추출하는 메서드(getDistanceAsDouble()) 추가

**적용 코드 (해결 후 코드)**
```
public class Path {
@JsonProperty("총길이")
private String distance; // 문자열로 변경하여 저장

    public double getDistanceAsDouble() {
        try {
            return Double.parseDouble(distance.replaceAll("[^0-9.]", "")); // 숫자와 점만 남김
        } catch (NumberFormatException e) {
            return 0.0; // 변환 실패 시 기본값 반환
        }
    }
}
```
**해결 결과 및 개선점**
- 잘못된 거리 데이터("11.8+13")도 정상적으로 숫자로 변환 가능
- 거리 값이 비어 있거나 잘못된 경우 기본값(0.0) 반환하여 예외 방지

**추가 개선 방향**
- 데이터 입력 시 "11.8+13" 같은 잘못된 형식을 방지하는 사전 검증 로직 추가 필요

<h3>3. LinkedHashMap 변환 오류 (ClassCastException)</h3>

**문제 상황**
- 제네릭 타입 정보가 런타임에서 손실됨
- JsonParser에서 데이터를 변환할 때 List<T>를 반환하는 제네릭을 사용했지만,
런타임에서 Jackson이 정확한 타입 정보를 얻지 못하고 기본적으로 LinkedHashMap을 반환하는 문제 발생

**문제 원인 분석**

- new TypeReference<List<T>>() {} 방식은 런타임에 정확한 타입 정보를 알 수 없음
- T가 런타임에 구체적인 클래스로 지정되지 않아 Jackson이 기본적으로 LinkedHashMap으로 변환 

**해결 방법 탐색**

- Class<T>를 매개변수로 추가하여 변환할 클래스 타입을 명확하게 지정
- 제네릭 기반 JSON 변환을 개선하여 올바른 객체로 변환되도록 수정

**적용 코드 (해결 후 코드)**
```java
public class JsonParser {
private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> parse(String filePath, Class<T> clazz) {
        try {
            File file = new File(filePath);
            if (!file.exists()) return new ArrayList<>();

            JsonNode rootNode = objectMapper.readTree(file);
            return objectMapper.readValue(rootNode.toString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
```
**해결 결과 및 개선점**

- LinkedHashMap 변환 문제 없이 JSON 데이터를 정확하게 객체로 변환 가능
-  parse("users.json", User.class), parse("routes.json", Path.class) 형태로 올바른 타입 변환 가능

**추가 개선 방향**

- Jackson의 타입 참조(TypeReference<T>)를 활용한 변환 방식도 고려하여 더욱 유연한 설계 가능

<div markdown="5">
</div>
</details>



