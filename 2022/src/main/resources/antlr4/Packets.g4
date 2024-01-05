grammar Packets;

list: '[' value? (',' value)* ']';
value: list | INT;
INT: [0-9]+;
WS: [ \t\r\n]+ -> skip;
