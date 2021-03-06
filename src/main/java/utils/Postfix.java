package utils;

import java.util.Stack;

public class Postfix {
//靳辰辰 start
    private StringBuilder postfix = new StringBuilder();
    private Stack<Character> opt = new Stack<>();
    private char[] c;

    public Postfix(String exp){
        this.c = exp.toCharArray();
    }

    public Postfix() {
    }

    //判断数字
    boolean isNum(char c){
        return ( c>=48 && c<=57 )|| c=='.';
    }
    //判断算符优先级
    private int prio(char a){
        int priority_a=0;
        switch (a){

            case '+': priority_a = 0;break;
            case '-': priority_a = 0;break;
            case '*': priority_a = 1;break;
            case '/': priority_a = 1;break;
            case '%': priority_a = 1;break;
            case '^': priority_a = 2;break;    //乘方
            case 'q': priority_a = 2;break;    //开二次根式
            case 'a': priority_a = 2;break;    //绝对值
            case 's': priority_a = 3;break;    //sin
            case 'c': priority_a = 3;break;    //cos
            case 't': priority_a = 3;break;    //tan
            case 'x': priority_a = 3;break;    //exp
            case 'l': priority_a = 3;break;    //ln
            case 'g': priority_a = 3;break;    //log


        }
        return  priority_a;
    }

    public String nifixToPostfix() {

        //遍历中缀表达式，请查看上面的规则
        for (int i=0;i<c.length;i++) {
            if (isNum(c[i])){
                postfix.append(c[i]);
                int flag = i;
                //如果当前字符为数字且下一位不数字，或当前数字已是中缀表达式字符串最后一位，在多位数后添加" "来分隔
                if ( (i < c.length-1&&!isNum(c[flag+1]))|| i == c.length-1)
                    postfix.append(" ");
                continue;
            }

            //识别对数log
            if (c[i]=='l'&&c[i+1]=='o'&&c[i+2]=='g'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i+2]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i+2]);
                i+=2;
                continue;
            }

            //识别对数ln
            if (c[i]=='l'&&c[i+1]=='n'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                i+=1;
                continue;
            }
            //识别绝对值

            if (c[i]=='a'&&c[i+1]=='b'&&c[i+2]=='s'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                i+=2;
                continue;
            }

            //识别二次根式
            if (c[i]=='s'&&c[i+1]=='q'&&c[i+2]=='r'&&c[i+3]=='t'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i+1]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i+1]);
                i+=3;
                continue;
            }

            //识别指数
            if (c[i]=='^'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                continue;
            }



            //识别正弦
            if (c[i]=='s'&&c[i+1]=='i'&&c[i+2]=='n'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                i+=2;
                continue;
            }
            //识别余弦
            if (c[i]=='c'&&c[i+1]=='o'&&c[i+2]=='s'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                i+=2;
                continue;
            }
            //识别正切
            if (c[i]=='t'&&c[i+1]=='a'&&c[i+2]=='n'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                i+=2;
                continue;
            }

            //识别左括号直接入操作符栈
            if (c[i]=='('){
                opt.push(c[i]);
                continue;
            }
            //识别乘除
            if (c[i]=='*'||c[i]=='/'||c[i]=='%'){
                while (!opt.isEmpty()&&(prio(opt.peek())>prio(c[i]))){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                continue;
            }
            //识别左括号直至遇见右括号否则将栈中操作符压出添加至后缀串
            if (c[i]==')'){
                while ((!opt.isEmpty())) {
                    if (opt.peek()=='('){
                        opt.pop();
                        break;
                    }
                    postfix.append(opt.pop());
                }
                continue;
            }
            //识别加减
            if (c[i]=='+'||c[i]=='-'){
                while ((!opt.isEmpty())&&opt.peek()!='('){
                    postfix.append(opt.pop());
                }
                opt.push(c[i]);
                continue;
            }

            //当前字符为自然对数按数字处理
            if (c[i]=='e'||c[i]=='E'){
                postfix.append('e');
                postfix.append(" ");
                continue;
            }
            //当前字符为圆周率按数字处理
            if (c[i]=='π'||c[i]=='Π'){
                postfix.append('π');
                postfix.append(" ");
                continue;
            }
        }

        //当遍历完中缀表达式，把此时符号栈的元素弹出，添加到后缀表达式
        while (!opt.isEmpty()){
            postfix.append(opt.pop());
        }
        return postfix.toString();
    }
//靳辰辰 end
}
