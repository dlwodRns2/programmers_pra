fun main(){
    //1. val - 읽기 전용(재할당 불가)
    val name = "홍길동"
    println(name);

    //2. var - 변경 가능(재할당 가능)
    var age = 20
    age=21
    println(age)

    //3. 자료형 추론 - 자료형을 안 써도 값을 보고 컴파일러가 결정
    val a = 10
    val b = 10L
    val c = 10.5
    val d = 10.5f
    val e = 'A'
    val f = "A"
    val g = true

    // 추론된 자료형은 이렇게 알 수 있다.
    println(a::class.simpleName)
    println(b::class.simpleName)
    println(c::class.simpleName)
    println(d::class.simpleName)
    println(e::class.simpleName)
    println(f::class.simpleName)
    println(g::class.simpleName)

    //5. 자료형을 지정하지 않으면 반드시 초깃값이 있어야 한다.
    //"var z"만 선언 불가 : 컴파일 에러, 추론할 값이 없다
    val z: Int
    z=5
    println(z)

    var w: String
    w="처음"
    println(w)
    w="나중"
    println(w)

    //6. $ 기호로 문자열 출력하기(문자열 템플릿)
    //문자열 안에 $를 붙이면 변수의 값을 그 자리에 그대로 끼워 넣을 수 있다.
    //자바처럼 +로 일일이 이어 붙이지 않아도 되고, 읽기도 훨씬 편하다.
    val userName = "홍길동"
    val userAge=20

    println("이름: $userName, 나이: $userAge")

    //6-1. ${ } - 표현식(연산, 함수 호출, 프로퍼티 접근)을 넣을 때는 중괄호로 감싼다.
    println("내년 나이: ${userAge+1}살") //연산
    println("이름 길이: ${userName.length}글자") //프로퍼티 접근
    println("대문자: ${"kotlin".uppercase()}") //함수 호출
    println("성인인가? ${userAge>=19}") //비교 결과

    //6-2. $ 기호 자체를 출력하고 싶을 때는 역슬래시로 한다.
    val price = 1000
    println("가격은 \$$price 입니다.")

    //6-3. 여러줄 문자열 안에서도 똑같이 동작한다
    val profile = """
        === 회원 정보 ===
        이름: $userName
        나이: $userAge
        내년: ${userAge+1}
        """.trimIndent() //앞쪽 공통 들여쓰기 제거
    println(profile)



}