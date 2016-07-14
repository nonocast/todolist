package cn.nonocast.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="task")
public class Task extends ModelBase {
    @ManyToOne
    @JoinColumn(name="belongs_to")
    private User belongsTo;
    @NotNull
    private String content;

    @Enumerated(EnumType.ORDINAL)
    private TaskCategory category;

    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;

    private int zindex;
    private Boolean topmost;
    private Date topmostedAt;
    private Date closedAt;

    public User getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(User belongsTo) {
        this.belongsTo = belongsTo;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public enum TaskCategory {
        LONGTERM("长期目标"),
        SHORTTERM("短期目标"),
        DAILY("当日目标");

        private String name;

        TaskCategory(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum TaskStatus {
        OPEN("活动"),
        CLOSE("关闭");

        private String name;

        TaskStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
