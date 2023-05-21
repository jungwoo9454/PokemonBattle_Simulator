public class Pokemon {

    public Pokemon(int _index, String _name, int _attack, int _deffence, int _hp, String _type)
    {
        index = _index;
        name = _name;
        attack = _attack;
        deffence = _deffence;
        hp = _hp;
        type = _type;

        Maxhp = hp;
    }
    public int index;
    public String name;
    public int attack;
    public int deffence;
    public int hp;
    public String type;

    int Maxhp;

    public int FirstAttack;
    public int SecondAttack;

    public void Init()
    {
        hp = Maxhp;
    }

    @Override
    public String toString() {
        //return "index=" + index + ", name=" + name + ", attack=" + attack + ", deffence=" + deffence + ", hp=" + hp + ", type=" + type;
        return name + " : 선공=" + FirstAttack + ", 후공=" + SecondAttack+ ", 총승수=" + (FirstAttack + SecondAttack);
    }
}
