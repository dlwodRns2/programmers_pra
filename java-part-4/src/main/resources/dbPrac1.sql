show databases;
use scott;
show tables;
select * from emp;
desc emp;

select distinct job from emp;
select all job from emp;
select job from emp;

#연습문제
#1. job이 CLERK인 데이터 조회
select * from emp where job='CLERK';

#2. sal이 1000 이상인 데이터 조회
select * from emp where sal>=1000;

#3. comm이 null인 데이터만 조회
select * from emp where comm is null;

#4. comm이 null이 아닌 데이터만 조회
select * from emp where comm is not null;

#5. 사원이름에서 'R'로 끝나는 데이터 조회
select * from emp where ENAME like '%R';

#6. 이름이 M으로 시작, 부서번호가 10인 사람 조회
select * from emp where ENAME like 'M%' and DEPTNO = 10;

#7. 연봉이 1000~2000인 데이터(between 사용)
select * from emp where sal between 1000 and 2000;

#8. 연봉이 1000~2000인 데이터(부틍호 사용)
select * from emp where sal >= 1000 and sal<=2000;

#9. 연봉이 1000~2000인 데이터(in 사용), sal = 1000 또는 2000에 해당하는 직원 정보만 출력
select * from emp where sal in (1000,2000);

#10. 부서번호 중복 제거
select distinct  EMP.DEPTNO from emp;

#11. 연봉순 정렬 각각 내림차순, 오름차순
select * from emp order by sal asc;
select * from emp order by sal desc;


#1. EMP 테이블에서 연봉 추출
select sal from emp;

#2. 사원번호 기준 각각 오름차순, 내림차순 하시오
select * from emp order by EMPNO asc;
select * from emp order by EMPNO desc;

#3. emp 테이블의 전체 열을 부서번호(오름차순)와 급여(내림차순)으로 정렬
# 부서번호(1순위)로 오름차순 정렬 => 부서번호가 같으면 급여(2순위) 기준으로 내림차순 정렬
select * from emp order by DEPTNO asc, sal desc;

#4. 사원번호가 7782인 사원 정보만 출력
select * from emp where EMPNO=7782;

#5. 부서번호가 20이거나 사원번호가 7782인 경우
select * from emp where DEPTNO=20 or EMPNO=7782;
#6. 부서번호가 20이면서 연봉이 1000보다 높은 직원
select * from emp where DEPTNO=20 and sal>1000;

#7. 급여가 2500 이상, 직업이 Anaylist인 사원 정보만 출력
select * from emp where sal>=2500 and JOB = 'ANALYST';

#8. 연봉이 3000이 아닌 경우 정보 출력
select * from emp where sal!=3000;
select * from emp where sal<>3000;
select * from emp where not sal=3000;

desc emp;
#9. 사원의 번호, 이름, 급여, 부서번호를 출력하시오
select EMP.EMPNO, EMP.ENAME, EMP.DEPTNO from emp;
select ename,sal,(sal*0.13) as bonus,deptno from emp;

#10. 입사일이 '1980-12-17'이상인 데이터 조회
select * from emp where HIREDATE>='1980-12-17';



