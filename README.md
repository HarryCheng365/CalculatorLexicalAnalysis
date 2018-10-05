# CalculatorLexicalAnalysis
Part of the whole Lexical Analysis

CMM文法描述：

Program->stmt-sequence

stmt-sequence->statement|stmt-sequence|**ε** 

statement->assign-stmt|declare-stmt

<声明>-><变量声明>                      

<变量声明>-><类型分类符> Identifier';'      问题：如何同时声明多个变量

<赋值语句>-><变量>'='<表达式>';'|';'

<变量>->Identifier

<表达式>-><因子><操作符><因子>|<因子>

<因子>->'('<表达式>')'|<变量>|<数字>



<操作符>-><加法操作符>|<乘法操作符>

<类型分类符>->'int'|'bool'|'float'

<加法操作>->‘+’|‘-’

<乘法操作>->‘*’|‘/’|‘%’

<数字>->0|1|2|3|4|5|6|7|8|9



int a, int b;

a=2;

b=3;

int c=a+b;

int d;

d=a*b;



