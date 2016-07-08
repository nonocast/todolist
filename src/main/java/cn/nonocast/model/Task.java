package cn.nonocast.model;


import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Task extends ModelBase {
    private Long belongsTo;
    private String content;
    private int category;
    private int status;
    private int zindex;
    private Boolean topmost;
    private Date topmostedAt;
    private Date closedAt;

    public Long getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Long belongsTo) {
        this.belongsTo = belongsTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getZindex() {
        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }

    public Boolean getTopmost() {
        return topmost;
    }

    public void setTopmost(Boolean topmost) {
        this.topmost = topmost;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public Date getTopmostedAt() {
        return topmostedAt;
    }

    public void setTopmostedAt(Date topmostedAt) {
        this.topmostedAt = topmostedAt;
    }

    public Task() {

    }

    public Task(String content) {
        this();
        this.content = content;
    }
}
