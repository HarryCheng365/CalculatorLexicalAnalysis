# CalculatorLexicalAnalysis
Part of the whole Lexical Analysis

## CMM综述：

## 词法规则

- 整数

```
digit ::= 0|1|2|3|4|5|6|7|8|9
integer ::= digit*
```

- 布尔值

```
boolean ::= true|false
```

- 标识符

```
alphabet ::= [a-z]|[A-Z]
identifier_body ::= alphabet|'_'|digit
identifier_head ::= alphabet|'_'
identifier ::= identifier_head identifier_body*
```

- 双精度浮点数

```
double ::= digit*.dight*
```

- 字符串

```
ASCII ::= ASCII码0-255(256个值)
char ::= 'ASCII'
String ::= "ASCII*"
```

- 保留字

```
if
else
while
for
true
false
int
bool
double
char
String
print
```



## 语法规则

- 语句

```
Program ::= stmt-sequence
stmt-sequence ::= statement|stmt-sequence|ε 
statement ::= assign-stmt|declare-stmt
```

- 声明语句

```
declare-stmt ::= Variable-stmt
type ::= int|bool|float|string
Variable-stmt ::= type identifier（下一步支持声明多个变量）
```

- 赋值语句

```
value ::= integer|boolean|double (浮点数计算 再说)
variable ::= value|identifier
assign-stmt ::= identifier=(variable|expression)
```

- 输出语句

``` 
output-stmt ::= print(value)
```

- 表达式

```
expression ::= factor op factor|factor
factor ::= (exprssion)|variable
op ::= add-op|mul-op|bool-op|Assign-op
add-op ::= '+'|'-'
mul-op ::= '*'|'/'|'%'
bool-op ::= '&&'|'||'|'=='|'!='|'>'|'<'|'>='|'<='
Assign-op ::= '='|'+='|'-='|'*='
```

- If | while| for 语句等 待添加
- 目前不支持 // 注释 或者 /* */ 注释
- 附 另一种语言描述方法

```
<声明>-><变量声明>                    

<变量声明>-><类型分类符> Identifier';'      问题：如何同时声明多个变量  有了

<赋值语句>-><变量>'='<表达式>';'|';'

<变量>->Identifier

<表达式>-><因子><操作符><因子>|<因子>

<因子>->'('<表达式>')'|<变量>|<数字>

<操作符>-><加法操作符>|<乘法操作符>

<类型分类符>->'int'|'bool'|'float'

<加法操作>->‘+’|‘-’

<乘法操作>->‘*’|‘/’|‘%’

<数字>->0|1|2|3|4|5|6|7|8|9
```



## 支持

- 支持0和正整数，正浮点数，布尔值
- 支持加法操作符，乘法操作符，赋值操作符，布尔运算符，复合操作符
- 支持字符和字符串，分别用‘  ‘和 “ ”作为标识
- 支持 ( ) , { } , [ ] 三种括号，以及 : 和 ;
- 支持 if , else, while, for 等关键字 并且只要与关键字字母组合相同(无视字母大小写)的标识符都将视为非法
- 支持以大小写字母和 _ 符号起始，接字母, _ , digit的标识符

int a, int b;

a=2;

b=3;

int c=a+b;

int d;

d=a*b;



