package com.example.simon.opgave;

public class User
{
    private int _id;
    private String username;
    private String password;
    private int score;

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public User()
    {
    }

    public User(String username, String password, int score)
    {
        this.username = username;
        this.password = password;
        this.score = score;
    }
}
