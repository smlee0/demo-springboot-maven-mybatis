# demo-springboot-maven-mybatis
Spring-boot &amp; maven 기반 Mybatis 뼈대 프로젝트



# 주요사용기술 (예시로 작성됐으므로 추가 및 수정 필요)
> SpringBoot, SpringSecurity, Hibernate, QueryDSL, Thymeleaf
* SpringBoot _(v2.1.3.RELEASE)_
* Jackson Databind _(v2.9.7)_
* Apache HttpClient _(v4.5.1)_
* Bouncy Castle Provider _(v1.54)_
* Project Lombok _(v1.18.2)_
* SLF4J API Module _(v1.7.25)_
* [Thyemeleaf](https://www.thymeleaf.org/)
* [Thymeleaf Layout Dialect (v2.3.0)](https://ultraq.github.io/thymeleaf-layout-dialect/)

# 개발가이드
## 1. 코드 규칙
### 1.1. Code Conventions
프로그램을 작성하는데 있어서 자신이 작성한 프로그램을 다른 사람이 읽기 쉽도록 해야 하며 이 때 “자바 코드 규칙(Java Code Conventions)”과 같은 관례가 필요하다. 관례에 따라서 프로그램을 작성함으로써 그 프로그램에 대한 가독성(Readability)을 높일 수 있다.<br>
코드 규칙은 썬마이크로시스템즈의 자바 언어 코딩 표준(Java language coding standards)과 Google Java Style을 기반으로 작성 되었다.<br>
본 문서는 기본적인 자바 프로그래밍 지식이 있는 개발자를 대상으로 한다.

## 2. 소스 파일
### 2.1. 소스 코드 라인 수
2,000라인 이상 되는 클래스는 만들지 않는다.

### 2.2. 파일 인코딩
소스 파일 인코딩은 UTF-8로 한다.

### 2.3. 중괄호 (Braces)
if, else, for, while 문 사용시 반드시 중괄호를 사용한다.<br>
중괄호 앞에서 줄 바꿈 하지 않는다.<br>
여는 중괄호 다음에 줄 바꿈하고, 닫는 중괄호 전에 줄 바꿈 한다.<br>
if-else문, try-catch문엔 여는 중괄호와 닫는 중괄호가 한 라인에 있는 것을 원칙으로 한다.
```
예시)
return new MyClass() {
    @Override public void method() {
        if (condition()) {
            try {
                something();
            } catch (ProblemException e) {
                recover();
            }
        }
    }
};
```
빈 블록은 여는 중괄호와 닫는 중괄호를 한 라인에 둘 수 있다.
```
예시)
void doNothing() {}
```


### 2.4. 들여쓰기 (Indentation)
공백 4개를 사용하여 들여쓰기 하는 것을 원칙으로 한다.
탭을 사용한다면 공백 4개 길이로 설정하여 사용한다.
공백과 탭을 섞어서 들여쓰기 하지 않는다.

### 2.5. @Slf4j 로그 작성 방법
local, dev, stag에서는 debug & info로 사용할 예정이며, prod에서 info를 사용하여 불필요한 노출을 최소화한다.<br>
info만 사용해도 무방은 하나, 가급적 debug를 사용한다.
```
// 단일 로그
log.debug(">>> param1: {}", param1);
// 복수 로그
log.debug(">>> param1: {}, param2: {}", param1, param2);

// 아래와 같은 형태는 사용을 금지한다.
log.debug(">>> param1: " + param1 + ", param2: " + param2);
```

### 2.6. 주석 템플릿
가이드대로 기본 코드스타일을 지정이 되어있다면, /** 으로 코드 템플릿이 자동생성이 되지만 생성되지 않으면 아래와 같이 따른다.<br>
<br>
Class, Interface 등 파일에는 아래와 같은 주석 템플릿을 가급적 작성하여 추가한다.<br>
```
예시 템플릿
/**
 * 샘플게시판 컨트롤러
 * @filename BoardController.java
 * @author Lee Se Min
 * @since 2022-06-09
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
```

인스턴스, 메소드 등 기능에는 아래와 같은 주석 템플릿을 가급적 작성하여 추가한다.
```
예시 템플릿
/**
 * 샘플 리스트
 * @param model
 * @param req
 * @param boardRequest
 * @return String
 * @author Lee Se Min
 * @date 2022-06-09
 */
```

모든 SQL 문에는 아래와 같은 주석을 작성하여 추가한다.
```
예시)
SELECT /* BoardMapper.selectListCnt */
    COUNT(1)
FROM SAMPLE_BOARD SB
```

## 3. 명명규칙
### 3.1. 공통
이름은 영문과 숫자 그리고 underscore (‘_’) 만을 사용해서 만들어야 한다.

### 3.2. Package
소문자로만 만든다.<br>
두 단어로 이루어진 이름은 그냥 붙여서 쓴다.
```
예시)
com.example.deepspace               Good
not com.example.deepSpace           Bad
com.example.deep_space              Bad
```

### 3.3. Class, Interface
UpperCamelCase 규칙을 따르는 명사 또는 명사구로 만든다.
```
예시)
Character               Good
ImmutableList           Good
Immutable_List          Bad

List					Good
Readable				Good
```

### 3.4. Method, Variable
lowerCamelCase 규칙을 따르는 동사 또는 동사구로 만든다.
```
예시)
stop                    Good
sendMessage             Good
SendMessage             Bad
```



# 운영(prod) 환경 및 개발설정

# 스테이징(stag) 환경 및 개발설정

# 로컬(local) / 개발(dev) 환경 및  개발설정






