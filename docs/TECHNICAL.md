# PolyCafe – Hướng dẫn Xây dựng Ứng dụng Quản lý Bán hàng Cafe

## Giới thiệu Dự án

**PolyCafe** là một ứng dụng mẫu quản lý bán hàng cho chuỗi quán café FPT Polytechnic. Mục tiêu của dự án là xây dựng một phần mềm bán đồ uống đơn giản giúp nhân viên phục vụ và quản lý quán cafe thực hiện công việc nhanh chóng và thuận tiện. Ứng dụng cho phép tạo phiếu gọi đồ uống, cập nhật hóa đơn, thanh toán và thống kê doanh thu theo thời gian.

**Người dùng chính**: Ứng dụng có hai vai trò người dùng: Nhân viên bán hàng (thu ngân) và Nhân viên quản lý (quản lý cửa hàng). Mỗi vai trò có các chức năng khác nhau:

- **Nhân viên bán hàng**: Đăng nhập hệ thống, tạo phiếu bán hàng cho khách, thêm/sửa món trong phiếu, thanh toán hoặc hủy phiếu, và xem lịch sử bán hàng của chính mình.
- **Nhân viên quản lý**: Quản lý danh mục thức uống, quản lý các món đồ uống, quản lý thẻ khách (thẻ định danh dùng để giao đồ uống), quản lý nhân viên (tài khoản người dùng), theo dõi danh sách phiếu bán hàng, và xem doanh thu bán hàng theo khoảng thời gian.

**Công nghệ sử dụng**: Ứng dụng được phát triển bằng ngôn ngữ Java (Java Swing cho giao diện desktop) kết hợp với JDBC để kết nối và thao tác với Cơ sở dữ liệu SQL Server. Kiến trúc ứng dụng tuân theo mô hình MVC đơn giản gồm các lớp Model (Entity), View (giao diện Swing), Controller (xử lý logic) và DAO (Data Access Object cho truy xuất dữ liệu).

**Môi trường phát triển**: Dự án được xây dựng trên:
- JDK 8 (hoặc mới hơn) – đảm bảo cài đặt Java Development Kit.
- NetBeans 8.2+ (hoặc IntelliJ/Eclipse) – IDE hỗ trợ thiết kế giao diện Swing kéo thả và quản lý project Java dễ dàng.
- Microsoft SQL Server – Hệ quản trị CSDL để tạo cơ sở dữ liệu PolyCafe. Sinh viên có thể sử dụng SQL Server 2014+ hoặc SQL Server Express.

Ngoài ra, cần chuẩn bị JDBC Driver cho SQL Server (ví dụ: file mssql-jdbc.jar) và tích hợp vào project để ứng dụng có thể kết nối tới CSDL.

## Cài đặt Môi trường và Cấu trúc Dự án

### 1. Chuẩn bị môi trường

Trước khi bắt đầu, hãy đảm bảo máy tính đã cài đặt đúng phần mềm:

- **Java JDK**: Tải và cài đặt JDK (phiên bản 8 hoặc mới hơn). Thiết lập JAVA_HOME và cập nhật PATH nếu cần.
- **IDE (Môi trường phát triển)**: Cài đặt NetBeans IDE (khuyến nghị sử dụng bản NetBeans 8.2 hoặc Apache NetBeans mới nhất có hỗ trợ Swing). Bạn cũng có thể dùng IntelliJ IDEA hoặc Eclipse, tuy nhiên hướng dẫn sẽ dựa trên NetBeans cho thuận tiện.
- **SQL Server**: Cài đặt SQL Server và SQL Server Management Studio (SSMS) để quản lý cơ sở dữ liệu. Tạo một CSDL trống tên PolyCafe (có thể đặt tên khác nhưng nên nhất quán, ví dụ PolyCafeDB).
- **JDBC Driver**: Tải file driver JDBC cho SQL Server (ví dụ: sqljdbc42.jar). Trong NetBeans, thêm file .jar này vào Libraries của project (chuột phải project > Properties > Libraries > Add JAR) để đảm bảo ứng dụng có thể dùng JDBC.

### 2. Tạo project và cấu trúc thư mục

Mở NetBeans và tạo New Project > Java > Java Application đặt tên project là PolyCafe. Đảm bảo bỏ chọn tạo lớp main mặc định (vì ta sẽ tự quản lý các frame/dialog Swing). Sau khi tạo project, cấu trúc thư mục nguồn nên được tổ chức theo mô hình nhiều lớp:

- **Gói (package) model hoặc entity**: Chứa các lớp Java tương ứng với các thực thể (Entity) trong hệ thống (VD: Category, Drink, Card, User, Bill, BillDetail). Mỗi lớp gồm các thuộc tính đại diện cho cột trong bảng CSDL và phương thức getter/setter.
- **Gói dao**: Chứa các DAO (Data Access Object) – mỗi DAO là một interface + class triển khai để thực hiện thao tác CRUD với CSDL cho một entity. Ví dụ: CategoryDAO (interface) và CategoryDAOImpl (class thực thi), tương tự cho DrinkDAO, UserDAO, ... Tách biệt DAO giúp dễ quản lý mã truy vấn SQL.
- **Gói controller**: Chứa các lớp Controller chịu trách nhiệm xử lý logic nghiệp vụ và tương tác giữa DAO và giao diện. Mỗi chức năng chính sẽ có một controller (có thể dưới dạng interface + class implement, hoặc chỉ class). Ví dụ: LoginController, DrinkController, SalesController, RevenueController, v.v.
- **Gói ui (hoặc view)**: Chứa các màn hình giao diện Swing (JFrame/JDialog) cho từng chức năng. Ví dụ: LoginJDialog (đăng nhập), PolyCafeJFrame (màn hình chính), CategoryManagerJDialog (quản lý loại đồ uống), DrinkManagerJDialog (quản lý đồ uống), SalesJDialog (màn hình bán hàng), BillJDialog (phiếu bán hàng chi tiết), RevenueManagerJDialog (thống kê doanh thu), v.v.
- **Gói helper hoặc util**: Chứa các lớp tiện ích dùng chung, chẳng hạn XJdbc (hỗ trợ kết nối CSDL, thực thi câu lệnh executeQuery/executeUpdate), XDate (xử lý định dạng ngày tháng), XImage/XIcon (xử lý đọc/ghi file hình ảnh cho sản phẩm), XAuth (lưu giữ thông tin người dùng đăng nhập hiện tại để kiểm tra phân quyền), v.v.

Sau khi tạo các package, thiết lập cấu trúc, hãy thêm driver JDBC vào Libraries như đã nói trên. Tiếp đó, tạo lớp tiện ích kết nối CSDL:

```java
public class XJdbc {
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dbUrl = "jdbc:sqlserver://localhost;databaseName=PolyCafeDB";
    static String user = "sa";       // chỉnh tài khoản SQL Server của bạn
    static String pass = "password"; // chỉnh mật khẩu tương ứng

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, pass);
    }
    public static int executeUpdate(String sql, Object...args) {
        // Thực thi INSERT, UPDATE, DELETE
        // (Triển khai prepare statement và xử lý tham số trong args)
    }
    // ... có thể bổ sung các phương thức tiện ích khác như executeQuery cho trường hợp SELECT.
}
```

Cấu hình trên chỉ là ví dụ; hãy đảm bảo thay đổi dbUrl, user, pass cho phù hợp với môi trường SQL Server của bạn. Tương tự, bạn có thể tạo lớp XQuery với các phương thức tiện dụng như getSingleBean(Class<T> cls, String sql, Object...args) hoặc getBeanList(Class<T> cls, String sql, Object...args) để truy vấn dữ liệu và map vào các đối tượng entity tương ứng (DAO sẽ sử dụng các hàm này để lấy dữ liệu).

### 3. Thiết lập cơ sở dữ liệu

Trong SQL Server, thực hiện tạo các bảng cần thiết cho hệ thống PolyCafe. Có thể chạy các lệnh T-SQL sau để tạo schema (hoặc sử dụng file script do giảng viên cung cấp):

```sql
-- Bảng Loại đồ uống (Category)
CREATE TABLE Categories (
    Id NVARCHAR(20) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);

-- Bảng Đồ uống (Drink)
CREATE TABLE Drinks (
    Id NVARCHAR(20) NOT NULL PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    UnitPrice FLOAT NOT NULL,
    Discount FLOAT NOT NULL,
    Image NVARCHAR(100) NOT NULL,
    Available BIT NOT NULL,
    CategoryId NVARCHAR(20) NOT NULL,
    FOREIGN KEY(CategoryId) REFERENCES Categories(Id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng Thẻ định danh (Card) – thẻ được phát cho khách để gọi món
CREATE TABLE Cards (
    Id INT NOT NULL PRIMARY KEY,
    Status INT NOT NULL  -- 0: Đang hoạt động, 1: Lỗi, 2: Thất lạc
);

-- Bảng Nhân viên (User) – tài khoản đăng nhập
CREATE TABLE Users (
    Username NVARCHAR(20) NOT NULL PRIMARY KEY,
    Password NVARCHAR(50) NOT NULL,
    Enabled BIT NOT NULL,    -- Trạng thái hoạt động (true= hoạt động)
    Fullname NVARCHAR(50) NOT NULL,
    Photo NVARCHAR(100) NOT NULL,
    Manager BIT NOT NULL     -- Vai trò (true = quản lý, false = nhân viên bán hàng)
);

-- Bảng Phiếu bán hàng (Bill)
CREATE TABLE Bills (
    Id BIGINT NOT NULL PRIMARY KEY IDENTITY(10000,1),
    Username NVARCHAR(20) NOT NULL,   -- người lập phiếu
    CardId INT NOT NULL,             -- thẻ phục vụ
    Checkin DATETIME NOT NULL,       -- thời điểm bắt đầu (mở phiếu)
    Checkout DATETIME NULL,          -- thời điểm thanh toán (đóng phiếu)
    Status INT NOT NULL,             -- 0: Chưa thanh toán, 1: Đã thanh toán, 2: Đã hủy
    FOREIGN KEY(Username) REFERENCES Users(Username) ON UPDATE CASCADE,
    FOREIGN KEY(CardId) REFERENCES Cards(Id) ON UPDATE CASCADE
);

-- Bảng Chi tiết phiếu (BillDetail)
CREATE TABLE BillDetails (
    Id BIGINT NOT NULL PRIMARY KEY IDENTITY(100000,1),
    BillId BIGINT NOT NULL,
    DrinkId NVARCHAR(20) NOT NULL,
    UnitPrice FLOAT NOT NULL,
    Discount FLOAT NOT NULL,
    Quantity INT NOT NULL,
    FOREIGN KEY(BillId) REFERENCES Bills(Id) ON DELETE CASCADE,
    FOREIGN KEY(DrinkId) REFERENCES Drinks(Id) ON UPDATE CASCADE
);
```

Lưu ý: Tên bảng trong CSDL dùng số nhiều (Categories, Drinks, ...), còn tên lớp Entity tương ứng trong Java ta đặt dạng số ít (Category, Drink, …) cho rõ nghĩa. Sau khi tạo bảng, có thể thêm một ít dữ liệu mẫu (ví dụ: một vài bản ghi Category, Drink, một vài thẻ Card, tài khoản User mẫu). Đặc biệt tạo trước một User có quyền quản lý (Manager = 1) để đăng nhập kiểm tra các chức năng quản trị.

## Phân tích Hệ thống

Trước khi lập trình, ta phân tích yêu cầu nghiệp vụ để hiểu rõ các chức năng cần có và luồng hoạt động của hệ thống PolyCafe.

### 1. Use Case và Chức năng của người dùng

Sơ đồ Use Case tổng quan của ứng dụng PolyCafe, hiển thị các chức năng chính cho hai vai trò: Nhân viên bán hàng (trái) và Nhân viên quản lý (phải). Nhìn vào sơ đồ Use Case ở trên, có thể mô tả các chức năng chính như sau:

**Nhân viên bán hàng** có các chức năng:
- **Đăng nhập**: Nhân viên phải đăng nhập vào hệ thống bằng tài khoản được cấp trước khi sử dụng các chức năng bán hàng.
- **Tạo phiếu bán hàng**: Khi khách hàng gọi đồ uống, nhân viên tạo một phiếu mới, chọn một thẻ định danh và ghi lại món khách gọi vào phiếu.
- **Xem/Sửa phiếu bán hàng đang phục vụ**: Trong thời gian phục vụ, nhân viên có thể thêm món mới hoặc điều chỉnh số lượng món trên phiếu của khách. Nhân viên cũng có thể thực hiện hủy phiếu (trong trường hợp khách hủy order) hoặc thanh toán (khi khách quyết định trả tiền).
- **Xem lịch sử bán hàng cá nhân**: Nhân viên có thể xem danh sách các phiếu bán hàng mà chính mình đã thực hiện trong một khoảng thời gian (lọc theo ngày, tuần, tháng, v.v), bao gồm chi tiết các phiếu đó.

(Lưu ý: Quyền của nhân viên bán hàng bị hạn chế: họ không thể truy cập các chức năng quản lý như thêm đồ uống hay xem doanh thu tổng thể. Họ chỉ thao tác được trên phiếu của mình và không được xem thông tin nhạy cảm như doanh thu hoặc xóa dữ liệu không thuộc phạm vi cho phép.)

**Nhân viên quản lý** có các chức năng:
- **Quản lý Đồ uống**: Thêm món đồ uống mới, cập nhật thông tin món (tên, giá, giảm giá, hình ảnh, trạng thái còn bán hay ngừng), xóa món khỏi thực đơn nếu cần, và xem danh sách tất cả đồ uống. Chức năng này thường bao gồm cả việc chọn ảnh minh họa cho đồ uống và phân loại món theo loại.
- **Quản lý Loại đồ uống**: Thêm/cập nhật/xóa các loại đồ uống (danh mục như Café, Trà, Sinh tố, v.v). Mỗi đồ uống thuộc một loại, do đó cần quản lý danh mục loại để dùng cho việc phân loại đồ uống.
- **Quản lý Thẻ định danh**: Thêm mới thẻ (nếu có thẻ vật lý mới đưa vào sử dụng), cập nhật trạng thái thẻ (khi thẻ bị hỏng hoặc thất lạc), hoặc loại bỏ thẻ không sử dụng. Thẻ định danh là các thẻ đánh số mà nhân viên đưa cho khách cầm tạm thời để nhận diện đơn hàng của họ.
- **Quản lý Nhân viên**: Thêm tài khoản nhân viên mới, chỉnh sửa thông tin (mật khẩu, tên, ảnh, trạng thái hoạt động), phân quyền nhân viên (quản lý hoặc bán hàng), hoặc khóa tài khoản (Enabled = false). Đặc biệt, quản lý cần đảm bảo không xóa chính tài khoản của mình hoặc hạ quyền khiến không còn ai có quyền quản lý.
- **Quản lý Phiếu bán hàng**: Xem danh sách tất cả các phiếu bán hàng được lập (bởi mọi nhân viên) trong hệ thống. Quản lý có thể lọc phiếu theo tình trạng (đang phục vụ, đã thanh toán, đã hủy) hoặc khoảng thời gian, xem chi tiết từng phiếu. Chức năng này giúp quản lý nắm được hoạt động bán hàng.
- **Xem Doanh thu**: Xem thống kê doanh thu bán hàng theo khoảng thời gian tùy chọn. Doanh thu có thể được tổng hợp theo loại đồ uống (để biết loại nào bán chạy, doanh thu bao nhiêu) hoặc theo nhân viên bán hàng (để đánh giá hiệu quả của từng nhân viên).

Tất cả các chức năng trên đều yêu cầu người dùng phải đăng nhập trước. Chức năng **Đổi mật khẩu** được cung cấp cho cả hai vai trò, cho phép người dùng thay đổi mật khẩu cá nhân khi cần.

### 2. Quy trình nghiệp vụ bán hàng (Business Flow)

Để hiểu rõ cách hoạt động của PolyCafe, xem xét quy trình bán hàng diễn ra tại quán cafe:

1. **Khách gọi đồ uống**: Nhân viên bán hàng tiếp nhận order và bắt đầu tạo một phiếu bán hàng mới trên phần mềm. Một thẻ định danh (thường là một thẻ nhựa ghi số) được chọn và đưa cho khách để sử dụng làm mã nhận đồ.
2. **Chuẩn bị và phục vụ**: Nhân viên pha chế chuẩn bị đồ uống theo thông tin trên phiếu. Nhân viên phục vụ sẽ giao đồ uống cho khách dựa trên số thẻ mà khách cầm (trùng với thẻ ghi trên phiếu).
3. **Khách gọi thêm (nếu có)**: Trong quá trình khách thưởng thức, nếu khách muốn gọi thêm đồ uống, nhân viên bán hàng sửa phiếu bán hàng tương ứng (tìm phiếu đang phục vụ theo mã thẻ) và thêm các món mới hoặc điều chỉnh tăng số lượng món.
4. **Thanh toán**: Khi khách yêu cầu thanh toán, nhân viên tiến hành đóng phiếu: tính tổng tiền (có áp dụng giảm giá nếu món có discount), thu tiền từ khách, in hóa đơn (nếu cần), thu hồi lại thẻ từ khách và trả thẻ đó về trạng thái sẵn sàng. Phiếu được cập nhật trạng thái Đã thanh toán cùng thời điểm checkout.
5. **Trường hợp thanh toán trước**: Đôi khi khách thanh toán ngay khi gọi (ví dụ mua mang đi). Lúc này nhân viên vẫn tạo phiếu và thu tiền trước; phiếu được đánh dấu đã thanh toán, nhưng thẻ định danh vẫn đưa cho khách cầm để nhận đồ. Khi giao đồ uống xong, phiếu coi như hoàn thành và thẻ được thu lại.
6. **Hủy phiếu**: Nếu khách hủy order (trước khi thanh toán) hoặc quán hết món không phục vụ được, nhân viên có thể hủy phiếu (trạng thái Đã hủy) và thu lại thẻ.

Quy trình trên đảm bảo mỗi khách hàng đang phục vụ sẽ tương ứng với một phiếu mở và được gán một thẻ định danh duy nhất. Nhân viên bán hàng quản lý các phiếu này trong ca làm việc của mình.

### 3. Mô hình dữ liệu – ERD

Cấu trúc dữ liệu của hệ thống được thiết kế theo mô hình quan hệ giữa các thực thể chính như sau:

*ERD – Sơ đồ Quan hệ Thực Thể của PolyCafe, biểu diễn các bảng CSDL và mối quan hệ giữa chúng.*

Trong ERD trên, chúng ta có các thực thể và quan hệ:

- **Category (Loại đồ uống)**: Mỗi loại đồ uống có mã Id (vd: CF, TEA, SMOOTHIE) và tên loại Name. Quan hệ 1-N với Drink: một Category có nhiều Drink.
- **Drink (Đồ uống)**: Mỗi đồ uống có mã Id (vd: CF01), tên Name, đơn giá UnitPrice, mức giảm giá Discount (ví dụ 0.1 nghĩa là giảm 10%), đường dẫn ảnh Image, trạng thái Available (còn bán hay ngừng bán), và CategoryId liên kết đến loại đồ uống. Drink thuộc về một Category (FK CategoryId).
- **Card (Thẻ định danh)**: Mỗi thẻ có số Id (số nguyên, vd 1,2,3…) và trạng thái Status (0: đang hoạt động sẵn sàng sử dụng, 1: thẻ lỗi hỏng, 2: thẻ thất lạc). Thẻ được dùng để gán cho phiếu bán hàng khi phục vụ khách. Một thẻ có thể được tái sử dụng cho nhiều phiếu khác nhau (nhưng không đồng thời).
- **User (Nhân viên)**: Thể hiện tài khoản đăng nhập của nhân viên. Thuộc tính gồm Username (mã đăng nhập, cũng là khóa chính), Password (mật khẩu mã hóa hoặc plaintext tùy triển khai), Enabled (trạng thái hoạt động của tài khoản), Fullname (họ tên nhân viên), Photo (tên file ảnh nhân viên), Manager (quyền quản lý: true nếu là quản lý, false nếu là nhân viên thường). Quan hệ: User gắn liền với Bill (một nhân viên có thể lập nhiều phiếu).
- **Bill (Phiếu bán hàng)**: Mỗi phiếu có mã số Id (sinh tự động, định dạng BIGINT, ví dụ 10000,10001…), thuộc tính Checkin (thời điểm mở phiếu), Checkout (thời điểm thanh toán, có thể null nếu chưa thanh toán), Status (0=Đang phục vụ, 1=Đã thanh toán, 2=Đã hủy). Bill liên kết tới User (Username của nhân viên lập phiếu) và Card (CardId – thẻ dùng cho phiếu). Quan hệ: một User có thể lập nhiều Bill; một Card có thể được gán cho nhiều Bill (khác thời điểm); ngược lại mỗi Bill chỉ do một User lập và gắn một Card.
- **BillDetail (Chi tiết phiếu)**: Mỗi phiếu bán hàng có các dòng chi tiết liệt kê từng món đồ khách gọi. Bảng BillDetails có khóa chính Id (BIGINT tự sinh), thuộc tính Quantity (số lượng), UnitPrice (đơn giá tại thời điểm bán), Discount (mức giảm giá tại thời điểm bán). BillDetail liên kết tới Bill (BillId) và Drink (DrinkId). Quan hệ: một Bill có nhiều BillDetail; mỗi BillDetail thuộc về một Bill và một Drink. Thông tin đơn giá và giảm giá được lưu vào BillDetail để không bị ảnh hưởng nếu sau này giá gốc của món thay đổi.

Như vậy, quan hệ tổng quát:
- Category 1 – n Drink
- Drink 1 – n BillDetail
- Bill 1 – n BillDetail
- User 1 – n Bill
- Card 1 – n Bill

Mô hình dữ liệu trên hỗ trợ đầy đủ các nghiệp vụ: quản lý danh mục (Category, Drink, User, Card) và giao dịch bán hàng (Bill, BillDetail).

## Thiết kế Ứng dụng

Sau khi phân tích, bước thiết kế tập trung vào kiến trúc tổng thể, các thành phần và giao diện người dùng trước khi triển khai code. Mục tiêu là xác định rõ cách các phần tương tác với nhau (giao diện – xử lý – dữ liệu) và đảm bảo các chức năng đáp ứng yêu cầu.

### 1. Kiến trúc và thành phần chính

Ứng dụng PolyCafe được thiết kế theo hướng tầng (layered), tách biệt phần giao diện, logic nghiệp vụ và truy cập dữ liệu:

- **Lớp Entity (Model):** Gồm các lớp Java tương ứng với bảng CSDL, dùng để lưu trữ trạng thái dữ liệu trong chương trình. Mỗi đối tượng entity (ví dụ Drink) đại diện cho một bản ghi trong bảng (ví dụ một món đồ uống cụ thể).

- **Lớp DAO (Data Access Object):** Cung cấp các chức năng CRUD (Create, Read, Update, Delete) và truy vấn dữ liệu từ CSDL cho một loại thực thể. Mỗi loại entity có một DAO interface mở rộng từ CrudDAO tổng quát. Ví dụ:

    - `CrudDAO<Entity, Key>` – interface tổng quát với các phương thức: `Entity create(Entity)`, `void update(Entity)`, `void deleteById(Key id)`, `List<Entity> findAll()`, `Entity findById(Key id)`.

    - `CategoryDAO extends CrudDAO<Category, String>`, `DrinkDAO extends CrudDAO<Drink, String>`, `UserDAO extends CrudDAO<User, String>`, v.v. Tùy trường hợp, DAO cụ thể có thể bổ sung phương thức đặc thù, chẳng hạn `DrinkDAO` thêm `List<Drink> findByCategoryId(String categoryId)` để truy vấn đồ uống theo loại.

    - Mỗi DAO có lớp cài đặt (DAOImpl) chứa mã JDBC thực thi các phương thức. Các câu lệnh SQL được đặt tại DAOimpl, sử dụng `XJdbc`/`XQuery` để kết nối và lấy dữ liệu. Ví dụ, `CategoryDAOImpl.create(Category entity)` sẽ chạy câu lệnh `INSERT INTO Categories(Id, Name) VALUES(?, ?)` với tham số từ entity

- **Lớp Controller (Điều khiển):** Lớp trung gian, xử lý logic nghiệp vụ và điều phối dữ liệu giữa DAO và View. Ở đây áp dụng mô hình thiết kế Controller cho từng màn hình: Mỗi màn hình (JDialog/JFrame) sẽ implement một interface Controller tương ứng để xử lý các hành vi. Ví dụ:

    - `LoginController` (xử lý đăng nhập),
    - `CategoryController` (xử lý các thao tác trên màn hình quản lý Category),
    - `SalesController` (xử lý logic màn hình bán hàng),
    - `RevenueController` (xử lý màn hình thống kê doanh thu), v.v.

Các interface controller này có thể kế thừa từ một interface tổng quát nếu có điểm chung. Chẳng hạn có thể thiết kế interface `CrudController<T>` để gom các thao tác cơ bản trên form quản lý: `open()`, `setForm(T obj)`, `getForm()`, `fillToTable()`, `edit()`, `create()`, `update()`, `delete()`, `clear()`, cũng như các chức năng điều hướng bản ghi (`moveFirst/Previous/Next/Last`) và xử lý checkbox chọn nhiều (nếu có). CategoryController, DrinkController, UserController... có thể extends `CrudController` để hưởng các phương thức chung đó

Lớp giao diện (JDialog) sẽ implements interface Controller tương ứng. Bên trong JDialog, ta triển khai các phương thức được định nghĩa trong interface (chính là logic nghiệp vụ), và đồng thời gắn kết các phương thức này với sự kiện giao diện (event handlers của nút bấm, bảng, etc.). Ví dụ:

- Sự kiện mở form (windowOpened) sẽ gọi `controller.open()` để load dữ liệu ban đầu
- Nhấn nút "Thêm mới" gọi `create()`, nút "Cập nhật" `gọi update()`, v.v
- Nhấn nút điều hướng << gọi `moveFirst()`, etc.

Cách thiết kế này giúp tách bạch phần giao diện (khai báo event) với logic xử lý (nằm trong phương thức controller). Code trở nên rõ ràng: dễ kiểm soát luồng, tránh viết logic lẫn lộn trong mã giao diện.

- **Lớp View (Giao diện Swing):** Sử dụng JFrame và JDialog để tạo các cửa sổ giao diện cho người dùng thao tác. Mỗi chức năng chính có một màn hình hoặc hộp thoại:

    - `WelcomeJDialog:` Màn hình chào (splash screen) xuất hiện đầu tiên, hiển thị logo/loading trong vài giây khi khởi động ứng dụng. Sau đó tự động đóng và mở màn hình đăng nhập

    - `LoginJDialog:` Màn hình đăng nhập, yêu cầu nhập username & password và chọn “Đăng nhập”. Nếu thông tin hợp lệ, chuyển vào cửa sổ chính; nếu không, hiển thị thông báo lỗi và cho phép nhập lại.

    - `PolyCafeJFrame:` Cửa sổ chính của ứng dụng sau khi đăng nhập thành công. Cửa sổ này chứa menu hoặc các nút điều hướng đến các chức năng: Quản lý thức uống, Loại thức uống, Thẻ, Nhân viên, Bán hàng, Doanh thu, Đổi mật khẩu, Đăng xuất... Ví dụ có thể tổ chức dưới dạng một JTabbedPane hoặc các JButton mở JDialog tương ứng.

    Các JDialog quản lý: Bao gồm:
    CategoryManagerJDialog – Quản lý loại đồ uống.
    DrinkManagerJDialog – Quản lý đồ uống.
    CardManagerJDialog – Quản lý thẻ định danh.
    UserManagerJDialog – Quản lý nhân viên.
    BillManagerJDialog – (Tuỳ chọn) Quản lý phiếu bán hàng.
    RevenueManagerJDialog – Thống kê doanh thu.

    Các màn hình quản lý này thường có giao diện dạng **Master-Detail**: bên trên là bảng liệt kê các bản ghi (ví dụ bảng danh sách thức uống), bên dưới hoặc dialog bên cạnh là form chi tiết để nhập/xem thông tin. Trên form có các nút “Thêm [Mới]”, “Cập nhật”, “Xóa”, “Xóa form” và các nút điều hướng bản ghi `|< < > >|` để xem bản ghi đầu/trước/sau/cuối. Nếu có chức năng chọn nhiều và xóa nhiều, có thể dùng JCheckBox trong bảng để chọn nhiều dòng, kèm nút “Chọn tất cả/Bỏ chọn tất cả/Xóa các mục đã chọn”

    Đặc biệt, màn hình **DrinkManager** sẽ có thêm trường để chọn ảnh món đồ uống:

    - Chọn ảnh: thường có một JLabel để hiển thị hình đồ uống, khi nhấp vào sẽ mở hộp thoại file (JFileChooser) để chọn file ảnh từ máy. Ảnh được copy vào thư mục của project (ví dụ thư mục `images/`), đồng thời `XImage.save(File src)` hỗ trợ lưu file và `XImage.read(String filename)` để đọc file hiển thị lên label.

    - Kích thước ảnh hiển thị có thể được căn chỉnh cho vừa label bằng tiện ích `XIcon.setIcon(JLabel label, String filePath) `hoặc tương tự, giúp co giãn hình đúng tỉ lệ khung hình label

    Dữ liệu đường dẫn ảnh (tên file) được lưu vào trường `Image` của Drink. Khi load lên bảng, có thể hiển thị một thumbnail nhỏ hoặc chỉ hiển thị tên file.

    - **Màn hình Bán hàng:** Đây là giao diện dành cho nhân viên thu ngân để tạo và quản lý phiếu bán hàng đang phục vụ:

        - `SalesJDialog:` Cửa sổ quản lý bán hàng tổng quát. Thiết kế có thể dạng một màn hình chia hai phần:

            - Bên trái là danh sách các thẻ (Card) hiện có dưới dạng các ô hoặc nút (ví dụ một panel lưới các nút, mỗi nút ghi số thẻ). Panel này có thể sử dụng GridLayout 6 cột x n hàng (tự co giãn) để hiển thị toàn bộ các thẻ hiện có. Những thẻ đang phục vụ (đã có phiếu mở) có thể được tô màu khác hoặc disable.

            - Khi người dùng click chọn một thẻ, sự kiện sẽ gọi `showBillJDialog(cardId)` – hàm này kiểm tra xem thẻ đó có phiếu đang phục vụ chưa bằng cách gọi `BillDAO.findServicingByCardId(cardId)`. Nếu chưa có, nó sẽ tạo mới một Bill (mã thẻ, user hiện tại, thời gian mở là now, trạng thái = 0) và lưu vào CSDL. Sau đó, mở cửa sổ `BillJDialog` để nhân viên bắt đầu thêm món cho phiếu.

    - `BillJDialog:` Cửa sổ phiếu bán hàng chi tiết. Màn hình này cho phép thêm món đồ uống vào phiếu và cuối cùng hoàn tất thanh toán hoặc hủy phiếu. Thiết kế:
        Hiển thị thông tin chung: Số phiếu, số thẻ, nhân viên, thời gian mở.
        
        - Bên trái là bảng Categories, bên phải là bảng Drinks thuộc loại đang chọn. Người dùng chọn một loại ở bảng trái, bảng phải sẽ liệt kê các đồ uống thuộc loại đó (sử dụng D`rinkDAO.findByCategoryId()` để tải danh sách
        ). Khi chọn một đồ uống trên bảng phải, có nút “Thêm vào phiếu” để thêm món đó.
        
        - Phía dưới có bảng Chi tiết phiếu (các món đã gọi, số lượng, đơn giá, giảm giá, thành tiền). Có các nút +/- để tăng giảm số lượng món đã gọi, nút xóa món, v.v.
        
        - Nút “Thanh toán”: khi bấm sẽ cập nhật trạng thái phiếu thành Đã thanh toán (1), ghi nhận thời điểm Checkout (ngày giờ hiện tại), lưu các chi tiết phiếu vào CSDL (nếu chưa lưu hết), giải phóng thẻ (có thể đặt `Card.Status` về 0 nếu tạm dùng trạng thái, hoặc không cần vì thẻ không có trạng thái “đang sử dụng” trong bảng Cards – việc quản lý thẻ đang dùng do logic code xử lý).
        
        - Nút “Hủy phiếu”: cập nhật trạng thái phiếu thành Đã hủy (2) và ghi nhận kết thúc. Khi hủy có thể không tính Checkout.
        
        - Khi đóng `BillJDialog`, gửi sự kiện để `SalesJDialog` làm mới danh sách thẻ (enable lại thẻ vừa giải phóng).

    - **Lịch sử bán hàng cá nhân:** Quản lý có chức năng xem lịch sử của mọi nhân viên, nhưng mỗi nhân viên thường chỉ xem lịch sử của mình. Có thể thiết kế `HistoryJDialog` cho phép nhân viên lọc các phiếu mình lập theo khoảng thời gian. Giao diện tương tự Revenue (chọn mốc thời gian nhanh: hôm nay, tuần này…, hoặc nhập từ ngày đến ngày) và hiển thị bảng các phiếu đã thanh toán/hủy trong khoảng đó. Nhấp đúp vào một phiếu trong lịch sử có thể mở lại `BillJDialog` dạng xem chi tiết (không cho sửa). Chức năng này giúp nhân viên đối chiếu doanh số của riêng họ hoặc xem lại các order cũ khi cần.

- **Đổi mật khẩu:** Màn hình `ChangePasswordJDialog` nhỏ gọn, cho phép người dùng (đã đăng nhập) đổi mật khẩu. Yêu cầu nhập mật khẩu cũ, mật khẩu mới, và xác nhận mật khẩu mới. Khi nhấn Xác nhận, kiểm tra mật khẩu cũ có khớp với tài khoản hiện đăng nhập không (từ `XAuth.user` hoặc query lại DB để chắc chắn), kiểm tra mật khẩu mới có đáp ứng yêu cầu (độ dài tối thiểu, ký tự đặc biệt – tùy chính sách), sau đó cập nhật trường `Password` trong bảng Users và thông báo thành công. Nếu có bất kỳ lỗi (sai mật khẩu cũ, mật khẩu mới không khớp xác nhận, quá ngắn…), hiển thị message tương ứng cho người dùng nhập lại.

**Phân quyền giao diện:** Dựa trên thuộc tính `Manager` của User đăng nhập, cửa sổ chính có thể ẩn hoặc vô hiệu hóa các chức năng không phù hợp. Ví dụ, nếu một nhân viên bán hàng đăng nhập (Manager = false), thì trên `PolyCafeJFrame` sẽ chỉ hiển thị các nút: Bán hàng, Lịch sử cá nhân, Đổi mật khẩu, Đăng xuất. Các nút Quản lý (Đồ uống, Loại, Thẻ, Nhân viên, Doanh thu) nên bị ẩn hoặc khóa lại. Ngược lại, nếu người quản lý đăng nhập, hiển thị đầy đủ mọi chức năng. Điều này ngăn chặn truy cập trái phép ngay từ giao diện.

### 2. Thiết kế cơ sở dữ liệu và lớp dữ liệu

Phần này đã được đề cập ở mục ERD. Mỗi bảng có một lớp Entity tương ứng trong Java:
- Lớp `Category` với các thuộc tính `id`, `name`.
- Lớp `Drink` với `id, name, unitPrice, discount, image, available, categoryId`.
- Lớp `Card` với `id, status`.
- Lớp `User` với `username, password, enabled, fullname, photo, manager`.
- Lớp `Bill` với `id, username, cardId, checkin, checkout, status`.
- Lớp `BillDetail` với `id, billId, drinkId, unitPrice, discount, quantity`.

Mỗi lớp có phương thức xây dựng (constructor), getter, setter. Có thể override `toString()` cho các entity nếu cần (ví dụ `Category.toString()` trả về tên loại để khi đưa vào combobox sẽ hiện tên).

DAO cho mỗi entity thực hiện các thao tác:

- `create(...):` Chèn bản ghi mới vào bảng. Trả về đối tượng đã tạo (riêng BillDAO.create có thể sau khi insert xong, lấy giá trị Identity sinh cho Bill.id để cập nhật đối tượng).
- `update(...):` Cập nhật bản ghi theo khóa chính.
- `deleteById(...):` Xóa bản ghi theo khóa chính.
- `findAll():` Lấy tất cả bản ghi (dùng SELECT * FROM Table).
- `findById(...):` Lấy một bản ghi theo khóa chính.
- DAO đặc thù:

    - `CategoryDAO:` có thể không cần thêm gì ngoài CRUD.
    - `DrinkDAO:` thêm `findByCategoryId(String cateId)` để lấy danh sách đồ uống thuộc loại.
    - `CardDAO:` có thể thêm phương thức đặt tất cả thẻ về trạng thái sẵn sàng hoặc tìm thẻ theo trạng thái.
    - `UserDAO:` có thể thêm `findByUsernameAndPassword(user, pass)` hỗ trợ login (hoặc login có thể làm trực tiếp qua XQuery).
    - `BillDAO:` có `findByUserAndTimeRange(String user, Date begin, Date end)` để hỗ trợ lịch sử cá nhân; (int cardId) để tìm phiếu đang mở của một thẻ (nếu không có thì tạo mới phiếu).
    - `BillDetailDAO:` thường CRUD đủ dùng, hoặc thêm `findByBillId(billId)` để liệt kê chi tiết của một phiếu.

`Lớp tiện ích XJdbc & XQuery:` Mục đích là tránh lặp lại mã kết nối JDBC ở mọi nơi. Ví dụ:

- `XJdbc.executeUpdate(String sql, Object...args):` thực thi các câu lệnh `INSERT/UPDATE/DELETE`, trả về số dòng ảnh hưởng. Sử dụng `PreparedStatement` để truyền tham số (các tham số tương ứng với dấu ? trong câu SQL).
- `XQuery.getSingleBean(Class<T> type, String sql, Object...args):` thực thi câu `SELECT` trả về một kết quả (hoặc null nếu không có). Mã thực thi query, dùng ResultSet để lấy dữ liệu, sau đó tạo instance của type và gán các thuộc tính từ `ResultSet`. Có thể sử dụng Java Reflection để tự động map cột->field theo tên (nâng cao), hoặc làm thủ công: ví dụ với Class Category, ta lấy rs.getString("Id") và rs.getString("Name") rồi new Category(id, name).
- `XQuery.getBeanList(Class<T> type, String sql, Object...args):` tương tự nhưng duyệt `ResultSet` để tạo danh sách đối tượng.

### 3. Thiết kế Giao diện Người dùng

Giao diện tổng thể của ứng dụng cần trực quan, dễ thao tác cho người dùng cuối (nhân viên quán). Một số điểm thiết kế UI:

- Sử dụng tiếng Việt cho tất cả nhãn, nút trong giao diện (ví dụ “Đăng nhập”, “Mật khẩu”, “Thêm mới”, “Cập nhật”, “Xóa”, “Lọc”, “Từ ngày/Đến ngày”, “Doanh thu”, “Số lượng” v.v) để nhân viên dễ sử dụng.
- Sắp xếp các trường dữ liệu và bảng hợp lý. Ví dụ màn hình quản lý đồ uống:
    - Cột bảng: Mã, Tên đồ uống, Đơn giá, Giảm giá (%), Trạng thái (Còn bán/Hết bán).
    - Form thêm/ sửa: các trường nhập cho Mã, Tên, Giá, Giảm giá, dropdown chọn Loại (Category), checkbox “Còn kinh doanh”, ô chọn Ảnh.
- Với các giá trị đặc biệt như trạng thái hoặc vai trò, có thể dùng ComboBox hoặc RadioButton:
    - Trạng thái thẻ: 0=Đang hoạt động, 1=Thẻ lỗi, 2=Thất lạc. Trên giao diện có thể hiện thị dưới dạng text thay vì số, hoặc dùng một combobox với 3 tùy chọn tương ứng.
    - Vai trò nhân viên: Checkbox “Quản lý?” tick nghĩa là quản lý, không tick là nhân viên thường.
- Các màn hình danh sách có thể thêm chức năng tìm kiếm, lọc nếu cần (ví dụ: tìm đồ uống theo tên, tìm nhân viên theo tên). Đơn giản nhất có thể có một ô nhập và nút "Tìm kiếm" để lọc bảng theo chuỗi nhập.
- Màu sắc, bố cục: nên tuân thủ một mẫu nhất quán. Ví dụ, các màn hình quản lý có thể tái sử dụng cùng một thiết kế (các nút CRUD đặt cùng vị trí, bảng bên trên, form bên dưới). Điều này giúp sinh viên dễ cài đặt (có thể kế thừa hoặc sao chép template) và người dùng cũng quen thuộc giao diện.

**Các bước thiết kế cụ thể:**

- Vẽ phác thảo giao diện (trên giấy hoặc dùng công cụ) cho từng màn hình. Đảm bảo có đủ thành phần UI cho mọi chức năng.
- Mở NetBeans, tạo JDialog mới cho từng màn hình. Sử dụng Design mode kéo thả các Swing control (JLabel, JTextField, JButton, JTable, JComboBox, etc) vào vị trí mong muốn.
- Đặt tên biến (variable name) cho các control rõ ràng, ví dụ: `txtUsername, txtPassword, btnLogin, tblDrinks, cboCategory, chkAvailable, lblImage, btnAdd, btnUpdate, btnDelete, btnClear, tblBills, txtBeginDate, txtEndDate, btnFilter...`
- Sử dụng TabbedPane khi cần thiết: ví dụ màn hình Doanh thu có thể dùng JTabbedPane với hai tab “Theo loại đồ uống” và “Theo nhân viên” để chứa hai bảng khác nhau.
- Thiết lập model cho JTable: dùng DefaultTableModel đặt sẵn số cột và tên cột. Đánh dấu những cột nào cho phép edit (thường không cho phép edit trực tiếp trên bảng, trừ cột checkbox).
- Với bảng có cột checkbox (xóa nhiều item), ta có thể custom model để kiểu dữ liệu cột cuối là Boolean, và hiển thị dưới dạng checkbox.
- Thiết lập sự kiện (ActionListener, MouseListener) cho các nút và bảng:
    - Bảng (JTable) bắt sự kiện click chọn dòng để thực hiện edit() (đổ data lên form).
    - Nút Thêm/ Sửa/ Xóa/ Mới gọi các phương thức tương ứng.
    - Nút điều hướng gọi phương thức moveFirst/Next...
    - Các trường chọn file ảnh, chọn combobox cũng có thể bắt sự kiện nếu cần (vd khi đổi combobox loại đồ uống thì refresh - list đồ uống – nhưng thường list đồ uống dùng bảng khác nên khác màn hình).

Sau khi thiết kế giao diện tĩnh xong, tiến hành kết nối giao diện với code Controller ở bước lập trình.

## Lập trình Các Chức năng Chính

Phần này hướng dẫn chi tiết cách hiện thực từng chức năng quan trọng của PolyCafe, bao gồm cả mã giả và ví dụ code minh họa. Các chức năng được liệt kê theo thứ tự ưu tiên khi làm dự án (thường ta sẽ làm từ những cái cơ bản như đăng nhập, rồi đến quản lý danh mục, sau đó chức năng bán hàng, cuối cùng là thống kê):

### 1. Chức năng Đăng nhập
**Mục đích:** Xác thực người dùng vào hệ thống, phân quyền theo vai trò. 

Các bước thực hiện:

1. **Tạo giao diện LoginJDialog:** gồm các trường txtUsername, txtPassword và nút btnLogin, btnExit. Mặc định btnLogin là Default (nhấn Enter kích hoạt).
2. **Bắt sự kiện nút Đăng nhập:** Khi click hoặc nhấn Enter, lấy giá trị username, password từ các trường nhập.
3. **Kiểm tra hợp lệ đầu vào:** Nếu username hoặc password bỏ trống, hiển thị thông báo yêu cầu nhập (JOptionPane) và return.
4. **Kiểm chứng thông tin:** Gọi UserDAO.findById(username) để lấy User có username nhập vào (hoặc viết truy vấn SELECT với username/password).
- Nếu không tìm thấy User, báo lỗi "Tài khoản không tồn tại".
- Nếu tìm thấy nhưng mật khẩu không khớp, báo "Sai mật khẩu". (Mật khẩu có thể được mã hóa, nếu vậy cần mã hóa input trước khi so sánh).
- Nếu tài khoản Enabled = false, báo "Tài khoản đã bị khóa."
5. **Đăng nhập thành công:** Lưu thông tin người dùng hiện tại vào biến toàn cục (ví dụ XAuth.user = foundUser). Đóng màn hình Login và mở PolyCafeJFrame – màn hình chính. Tại đây, dựa trên user.manager để bật/tắt các chức năng phù hợp.
6. **Thoát:** Nút Thoát hoặc đóng cửa sổ đăng nhập sẽ thoát ứng dụng (System.exit(0)).

**Lưu ý bảo mật:**

- Không nên lưu mật khẩu plain text trong CSDL. Tuy nhiên, do đây là dự án mẫu cho sinh viên, có thể đơn giản lưu chuỗi mã hóa MD5 (hoặc SHA) của mật khẩu. Khi đăng nhập, mã hóa input rồi so sánh với mật khẩu lưu trong DB.
- Sau một số lần đăng nhập sai có thể khóa chức năng đăng nhập tạm thời hoặc đóng ứng dụng (tùy yêu cầu, bài mẫu có thể không yêu cầu nhưng nên lưu ý).

### 2. Chức năng Đổi mật khẩu

**Mục đích:** Cho phép người dùng tự thay đổi mật khẩu định kỳ, tăng bảo mật.

**Các bước:**

1. **Mở màn hình ChangePasswordJDialog:** Từ menu hoặc nút "Đổi mật khẩu" trên giao diện chính (chỉ hiển thị khi đã đăng nhập).
2. **Giao diện gồm:** txtOldPass, txtNewPass, txtConfirmPass, nút Xác nhận và Hủy.
3. **Xác nhận đổi mật khẩu:**
    - Kiểm tra input: mật khẩu cũ, mới, xác nhận không được rỗng. Mật khẩu mới đủ độ dài yêu cầu (ví dụ >= 6 ký tự).
    - Kiểm tra txtNewPass trùng với txtConfirmPass.
    - Kiểm tra mật khẩu cũ đúng: so sánh txtOldPass với mật khẩu của XAuth.user hiện tại (hoặc truy vấn DB để chắc chắn lấy dữ liệu mới nhất).
Nếu có bất kỳ lỗi nào, hiện thông báo và yêu cầu nhập lại.
4. **Cập nhật mật khẩu:** Nếu tất cả kiểm tra OK, gọi UserDAO.update(user) hoặc câu SQL UPDATE Users SET Password=? WHERE Username=?. Nên mã hóa mật khẩu mới trước khi lưu (nếu có dùng mã hóa).
5. **Phản hồi:** Thông báo "Đổi mật khẩu thành công". Đóng form hoặc yêu cầu đăng nhập lại tùy chính sách. (Thường có thể cho tiếp tục phiên làm việc, nhưng một số hệ thống logout để chắc chắn người dùng dùng mật khẩu mới).

### 3. Chức năng Quản lý Loại đồ uống (Category)

**Mục đích:** Quản lý các loại đồ uống để phân loại thức uống trong menu.

Các bước:

1. **Chuẩn bị DAO & Entity:** Đảm bảo có Category entity và CategoryDAO/CategoryDAOImpl đầy đủ CRUD.
2. **Thiết kế giao diện CategoryManagerJDialog:** Gồm bảng tblCategories với cột Mã loại, Tên loại, có thể thêm cột checkbox để chọn. Form chi tiết gồm txtCategoryId, txtCategoryName, và các nút Thêm, Cập nhật, Xóa, Mới, Điều hướng (First, Prev, Next, Last), Chọn tất cả, Bỏ chọn, Xóa mục chọn.
3. **Tải dữ liệu ban đầu:** Khi mở form (sự kiện windowOpened), gọi fillToTable():
    - Lấy tất cả categories từ DB (categoryDAO.findAll()), lưu vào List<Category> items.
    - Đưa dữ liệu vào tblCategories – sử dụng DefaultTableModel xóa hết dòng cũ và thêm dòng mới cho từng category. Có thể thêm một cột checkbox giá trị false mặc định.
4. **Thêm mới Category:** Khi nhấn Thêm:
    - Gọi getForm() để tạo một đối tượng Category từ dữ liệu trên form.
    - Kiểm tra hợp lệ: mã loại và tên loại không được trùng với bản ghi hiện có (có thể check qua findById hoặc duyệt list).
    - Gọi categoryDAO.create(category), nếu thành công:
        - Hiển thị thông báo "Thêm thành công".
        - Gọi lại fillToTable() để cập nhật bảng.
        - Gọi clear() để xóa trắng form (chuẩn bị cho lần nhập kế tiếp).
5. **Chọn dòng để Edit:** Khi người dùng chọn một dòng trên bảng (sự kiện mouseClicked hoặc khi bấm nút Sửa):
    - Lấy chỉ số dòng index = tblCategories.getSelectedRow().
    - Lấy Category entity = items.get(index) (vì items là list hiện đang hiển thị).
    - Đưa dữ liệu lên form qua setForm(entity).
    - Chuyển tab (nếu dùng TabbedPane để chứa form) sang tab Form chi tiết.
    - Chuyển trạng thái form sang chế độ edit: setEditable(true) – hàm này sẽ khóa trường mã (không cho sửa mã chính) và bật các nút Cập nhật/Xóa, tắt nút Thêm.
6. **Cập nhật Category:** Khi nhấn Cập nhật:
    - Tương tự Thêm, dùng getForm() tạo đối tượng, nhưng thực chất chỉ cần lấy txtName vì mã không đổi.
    - Gọi dao.update(entity) để lưu thay đổi.
    - Thông báo "Đã cập nhật".
    - Gọi fillToTable() để thấy dữ liệu mới trong bảng.
7. **Xóa Category:** Có hai cách xóa:
    - Xóa một: Khi đang ở trạng thái edit một bản ghi cụ thể, nhấn nút Xóa -> xác nhận và dùng dao.deleteById(maLoai). Sau đó fillToTable(). Chú ý ràng buộc khóa ngoại: chỉ cho xóa loại nếu không có đồ uống thuộc loại đó (FOREIGN KEY có ON DELETE CASCADE rồi, nhưng xóa loại sẽ xóa tất cả đồ uống liên quan – cần cân nhắc nghiệp vụ, thường không cho xóa nếu có đồ uống).
    - Xóa nhiều: Nếu giao diện có checkbox, người dùng có thể tick chọn nhiều dòng rồi nhấn "Xóa các mục đã chọn". Lúc này duyệt bảng, lấy những dòng có checkbox = true, gọi dao.deleteById cho từng mã. Sau cùng, refresh bảng. (Cẩn thận hiệu ứng xóa nhiều và index thay đổi, nên tốt nhất lặp từ cuối bảng về đầu để xóa hoặc thu thập list id rồi xóa).
8. **Các nút khác:**
    - Mới (Clear): Gọi clear() để xóa trắng các trường nhập, đặt trạng thái form về ban đầu (cho phép nhập mã mới). Thường cũng set lại setEditable(false) nghĩa là đang không edit bản ghi cũ nào.
    - Điều hướng: moveFirst() lấy phần tử đầu tiên từ list items và gọi setForm(), moveLast() lấy cuối cùng, v.v. Đồng thời cập nhật trạng thái các nút điều hướng (nếu đầu danh sách thì disable nút First/Prev).
    - Chọn tất cả/Bỏ chọn: Lặp tất cả các dòng bảng, set value cột checkbox tương ứng true/false.
    - Tìm kiếm (nếu có): thực hiện dao.findAll() rồi filter những category có name chứa từ khóa, hoặc có thể viết dao.findByName(keyword) nếu muốn. Cập nhật bảng với kết quả lọc.

Chức năng Quản lý Loại đồ uống khá đơn giản nhưng là nền tảng để làm Quản lý Đồ uống tiếp theo.

### 4. Chức năng Quản lý Đồ uống (Drink)

**Mục đích:** Quản lý thực đơn đồ uống của quán: thêm món mới, chỉnh sửa thông tin và ngừng bán món. 

Các bước:

**1. Chuẩn bị:** Đảm bảo đã làm xong Quản lý Loại đồ uống, vì thêm đồ uống cần chọn loại. Cài đặt `DrinkDAO/DrinkDAOImpl` với các phương thức CRUD và `findByCategoryId()`.

**2. Giao diện DrinkManagerJDialog:** Tương tự Category, nhưng phức tạp hơn một chút do có nhiều trường:
- Bảng tblDrinks với các cột: Mã, Tên, Đơn giá, Giảm giá, Loại, Trạng thái, Ảnh (có thể chỉ hiển thị biểu tượng hoặc chữ).
- Form gồm: txtDrinkId, txtDrinkName, txtUnitPrice, txtDiscount, cboCategory (combo chọn loại), chkAvailable (còn bán?), một lblImage (label hiển thị ảnh và nút "Chọn ảnh..." để thay đổi ảnh).
- Nút Thêm, Cập nhật, Xóa, Mới, điều hướng, có thể có chức năng Xóa nhiều tương tự.

**3. Tải dữ liệu:** Khi mở form:
- Load toàn bộ drinks: drinkDAO.findAll(), sau đó show vào tblDrinks.
- Cột Loại có thể hiển thị tên loại thay vì mã – có thể bằng cách join khi truy vấn hoặc sau khi lấy data thì với mỗi Drink, tìm Category tương ứng (từ một map category đã load trước).
- Cột Ảnh: có thể hiện text "Có" / "Không" nếu cần (hoặc hiển thị icon nhỏ nếu làm nâng cao).
- Load combobox Loại: sử dụng categoryDAO.findAll() để lấy list Category, rồi set model cho cboCategory với category.getName() (có thể override toString() của Category để trả về name, khi đó combo hiển thị name nhưng khi lấy selectedItem cast về Category sẽ có đủ).

**4. Thêm mới Drink:**
- Kiểm tra mã đồ uống (Id) không trùng (findById).
- Lấy dữ liệu từ form: mã, tên, giá, giảm giá (convert về float, có thể yêu cầu 0 <= discount < 1.0), trạng thái, loại (lấy ((Category)cboCategory.getSelectedItem()).getId()), ảnh (tên file ảnh, nếu lblImage.getIcon() != null).
- Tương tự gọi drinkDAO.create(drink), cập nhật bảng và clear form.
- Xử lý phần ảnh:
    - Nếu người dùng chọn ảnh: ta copy file ảnh vào thư mục tài nguyên (ví dụ images/ trong cùng folder jar hoặc thư mục project). Dùng XImage.save(File) để lưu và trả về tên file hoặc đường dẫn đã lưu. Lưu tên file (hoặc đường dẫn tương đối) vào thuộc tính Image của Drink.
    - Nếu người dùng không chọn ảnh, có thể đặt ảnh mặc định (ví dụ default.png) cho mọi đồ uống chưa có ảnh.
- Sau khi thêm thành công, ảnh hiển thị trên form nên xóa hoặc về ảnh mặc định.

**5. Sửa Drink:**
- Không cho phép sửa mã (giống Category), các bước tương tự thêm.
- Nếu thay đổi ảnh: cho phép chọn ảnh mới và ghi đè ảnh cũ hoặc lưu ảnh mới. Cần quyết định có xóa file ảnh cũ không
- có thể không cần trong phạm vi bài tập.
- Cập nhật DB qua drinkDAO.update(drink).
- Cập nhật lại dòng tương ứng trong bảng (hoặc reload toàn bảng).

**6. Xóa Drink:**
- Cho phép xóa nếu đồ uống đó hiện không nằm trong phiếu nào (ràng buộc khóa ngoại BillDetail.DrinkId có ON DELETE CASCADE – xóa Drink sẽ xóa luôn BillDetail liên quan. Tuy nhiên nghiệp vụ thực tế thường không xóa dữ liệu giao dịch, thay vào đó ngừng bán: do đó có thể quy định không thực sự xóa đồ uống mà chỉ đánh dấu Available=false. Nếu vậy, nút Xóa có thể được thay bằng “Ngừng bán” = đổi trạng thái).
- Nếu vẫn muốn cho xóa: thực hiện drinkDAO.deleteById(id).
- Xóa file ảnh nếu cần (xóa file trong thư mục ảnh, cẩn thận nếu nhiều món dùng chung ảnh nhưng trường hợp này chắc không có).

**7. Tìm kiếm/Lọc: Có thể thêm combobox lọc theo Loại ngay trên giao diện:**
- Khi chọn một loại trong combobox (ngoài combo trong form chi tiết), thì chỉ hiển thị những drink thuộc loại đó. Hoặc có thể đơn giản: double click vào một loại trong màn hình Category sẽ mở màn hình Drink và tự động lọc theo loại.
- Thêm ô tìm theo tên để filter list hiện tại (dùng stream().filter với list đã tải).

**8. Quản lý ảnh:** Kiểm tra kỹ đường dẫn ảnh khi đóng gói ứng dụng. Nên lưu ảnh trong thư mục cài đặt hoặc đường dẫn tương đối. Việc load ảnh dùng ImageIcon và có thể điều chỉnh kích thước cho vừa label:

```bash
// Ví dụ: hiển thị ảnh vừa JLabel
ImageIcon icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
lblImage.setIcon(icon);
```
Sử dụng các hàm tiện ích XImage/XIcon nếu đã viết để code gọn hơn.

### 5. Chức năng Quản lý Nhân viên (User)

**Mục đích:** Quản lý tài khoản người dùng của hệ thống.

Các bước:

**1. DAO & Entity:**
- UserDAO extends CrudDAO<User,String> (khóa là Username)
- Có thể bổ sung List<User> findByRole(boolean manager) để lọc, nhưng không bắt buộc.

**2. Giao diện UserManagerJDialog:**
- **Bảng tblUsers** với cột: Tên đăng nhập, Họ tên, Vai trò, Trạng thái, Ảnh
- **Form chi tiết** gồm:
  - txtUsername, txtPassword (cho trường hợp thêm người dùng mới), txtFullname
  - chkManager (hoặc radio Quản lý/Nhân viên), chkEnabled (kích hoạt)
  - lblPhoto để hiển thị ảnh nhân viên, nút chọn ảnh (hoặc có thể bỏ ảnh nếu không cần thiết cho bài tập)
  - Nút Thêm, Cập nhật, Xóa, Mới, điều hướng

**3. Thêm nhân viên:**
- Kiểm tra trùng username
- Mã hóa mật khẩu (nếu áp dụng)
- Đặt mặc định Enabled=true khi thêm mới, vì tạo user xong sẽ active ngay
- Lưu ý: nếu hệ thống có ít nhất một user quản lý, không nên cho phép đặt tất cả user đều thành nhân viên thường, sẽ không còn ai có quyền admin. Do đó, khi thêm user, nếu chọn Manager=true thì không vấn đề, nếu false cũng không vấn đề (miễn còn người khác là quản lý)
- Lưu user qua userDAO.create(user)

**4. Sửa nhân viên:**
- Cho phép sửa tên, ảnh, vai trò, trạng thái, mật khẩu (có thể làm chức năng đặt lại mật khẩu)
- Thông thường không cho sửa username (khóa chính). Nếu cần sửa username thì đó thực chất là tạo mới và xóa cũ, không khuyến khích
- Nếu sửa mật khẩu: có thể thêm một nút "Đặt lại mật khẩu" riêng – khi bấm sẽ đặt password = một giá trị mặc định (ví dụ "123456") rồi yêu cầu người dùng đổi khi đăng nhập
- Khi self-update (nhân viên đang đăng nhập tự sửa thông tin mình) thì cần cẩn thận: không cho tự khóa tài khoản mình, không cho tắt quyền quản lý của chính mình nếu chỉ còn mình là quản lý

**5. Xóa nhân viên:**
- Không cho phép xóa chính tài khoản đang đăng nhập (tránh self-delete)
- Nếu xóa nhân viên đã có phiếu bán hàng trong hệ thống: do ràng buộc khóa ngoại Bill.Username ON UPDATE CASCADE (không có ON DELETE), xóa user sẽ vi phạm. Vì vậy, thông thường không xóa nhân viên có dữ liệu liên quan, mà chỉ khóa (Enabled=false). Đó cũng là lý do có trường Enabled
- Vì thế, chức năng Xóa có thể chỉ đơn giản là: nếu user chưa lập phiếu nào (có thể kiểm tra billDAO.findByUser(user) rỗng) thì cho xóa hẳn; ngược lại, thay vì xóa ta nên gợi ý chuyển sang khóa tài khoản

**6. Quản lý ảnh:**
- Tương tự quản lý ảnh đồ uống nếu có (chọn ảnh chân dung cho nhân viên)
- Ảnh nhân viên có thể lưu cùng thư mục ảnh đồ uống nhưng nên tách (ví dụ folder photos/)

**7. Phân quyền hiển thị:**
- Tại màn hình này, có thể ẩn chính user hiện đang đăng nhập hoặc đánh dấu đặc biệt (ví dụ tô màu) để tránh thao tác nhầm

**8. Đổi mật khẩu vs Đặt lại mật khẩu:**
- Quản lý có thể có quyền đặt lại mật khẩu cho nhân viên quên mật khẩu
- Chức năng này có thể thêm: ví dụ một nút "Reset mật khẩu" đặt password của user được chọn = giá trị mặc định và bắt họ đổi lại khi đăng nhập

### 7. Chức năng Quản lý Phiếu bán hàng (Bills)

**Mục đích:** Cho phép quản lý xem toàn bộ các phiếu bán hàng do mọi nhân viên lập, hỗ trợ kiểm tra và quản lý hoạt động bán hàng.

**Ghi chú:** Chức năng này có thể không bắt buộc triển khai đầy đủ nếu đã có Lịch sử bán hàng cá nhân cho nhân viên và Doanh thu cho quản lý. Tuy nhiên, nếu làm, có thể làm dưới dạng xem danh sách phiếu và chi tiết.

Các bước:

**1. Giao diện BillManagerJDialog:**
- **Bảng tblBills** cột: Mã phiếu, Thẻ, Người lập, Giờ mở, Giờ đóng, Trạng thái
- Phía trên có thể có phần lọc: từ ngày - đến ngày, combobox trạng thái (Tất cả/Đang phục vụ/Đã thanh toán/Đã hủy), nút Lọc

**2. Tải dữ liệu:**
- Mặc định hiển thị các phiếu Chưa thanh toán (Status=0) nếu có, hoặc hiển thị phiếu của ngày hiện tại
- Cho phép lọc khoảng thời gian: ví dụ chọn hôm nay, tuần này, tháng này, hoặc chọn khoảng tùy ý
- Viết phương thức trong BillDAO: List<Bill> findByTimeRange(Date begin, Date end) (và có thể findByStatus(int status)), hoặc gộp thành findByConditions(begin, end, statusOpt)

**3. Xem chi tiết:**
- Khi click đúp một phiếu trong bảng, mở BillJDialog dưới dạng xem-only:
  - Truyền Bill được chọn vào dialog (dialog.setBill(bill, readOnly=true))
  - Trong BillJDialog, ẩn/khóa các nút thêm/xóa/đổi số lượng, chỉ hiển thị thông tin chi tiết phiếu (danh sách món)
  - Cho phép in phiếu (nếu làm thêm)

**4. Hủy phiếu:**
- Quản lý có thể hủy những phiếu đang phục vụ (ví dụ khách rời đi không thanh toán)
- Nút "Hủy" có thể cho phép trên màn hình này, sẽ đặt status=2 cho phiếu đang chọn
- Chỉ áp dụng phiếu đang ở trạng thái 0 (Servicing)
- Sau khi hủy, có thể cần giải phóng thẻ (nhưng trong SalesJDialog logic đã xử lý)

**5. Sửa phiếu:**
- Không cho phép chỉnh sửa nội dung phiếu từ đây – việc thêm món chỉ do nhân viên bán hàng thao tác lúc phục vụ
- Trường hợp đặc biệt: Nếu một phiếu bị treo (ví dụ nhân viên quên đóng phiếu), quản lý có thể sử dụng màn này để tìm và xử lý (đóng/hủy thủ công)

*Chức năng này mang tính hỗ trợ và giám sát. Tùy thời gian, có thể lồng ghép luôn vào màn hình Doanh thu (ví dụ cho phép click từng phiếu trong danh sách doanh thu để xem chi tiết).*

### 8. Chức năng Xem Doanh thu (Thống kê)

**Mục đích:** Cung cấp cho quản lý cái nhìn tổng quan về doanh thu bán hàng trong khoảng thời gian, theo hai tiêu chí: loại đồ uống và nhân viên bán hàng.

Các bước:

**1. Chuẩn bị:**
- Tạo lớp entity đặc biệt Revenue (hoặc có thể không cần entity riêng, dùng đối tượng dạng Map). Ở đây slide gợi ý tạo lớp Revenue chứa hai inner static class ByCategory và ByUser.
- Mỗi class chứa các trường tương ứng cho kết quả thống kê:
  - **Revenue.ByCategory**: category (tên loại), revenue (tổng doanh thu), quantity (tổng số lượng bán), minPrice, maxPrice, avgPrice (giá bán tối thiểu, tối đa, trung bình của các món trong loại đó)
  - **Revenue.ByUser**: user (tên đăng nhập hoặc tên nhân viên), revenue (tổng doanh thu người đó bán được), quantity (số phiếu hoặc số món? Slide dùng count(DISTINCT BillId) để đếm số phiếu), firstTime (thời điểm bán hàng đầu tiên trong khoảng), lastTime (thời điểm bán hàng cuối cùng trong khoảng)

**2. DAO:**
- Tạo RevenueDAO với hai hàm getByCategory(Date begin, Date end) và getByUser(Date begin, Date end) trả về List<Revenue.ByCategory> và List<Revenue.ByUser>
- Triển khai trong RevenueDAOImpl:
  - Sử dụng XQuery.getBeanList(Revenue.ByCategory.class, byCategorySql, begin, end), với byCategorySql là chuỗi SQL dùng GROUP BY loại đồ uống trong khoảng thời gian:

```sql
SELECT c.Name AS Category,
       SUM(d.UnitPrice * bd.Quantity * (1 - bd.Discount)) AS Revenue,
       SUM(bd.Quantity) AS Quantity,
       MIN(bd.UnitPrice) AS MinPrice,
       MAX(bd.UnitPrice) AS MaxPrice,
       AVG(bd.UnitPrice) AS AvgPrice
FROM BillDetails bd
  JOIN Drinks d ON d.Id = bd.DrinkId
  JOIN Categories c ON c.Id = d.CategoryId
  JOIN Bills b ON b.Id = bd.BillId
WHERE b.Status = 1 
  AND b.Checkout BETWEEN ? AND ?
GROUP BY c.Name
ORDER BY Revenue DESC;
```

*(Giải thích: Lọc Status=1 và có Checkout trong khoảng để chỉ tính phiếu đã thanh toán trong giai đoạn đó. Tính tổng doanh thu = đơn giá * số lượng * (1 - discount).)*

- Tương tự byUserSql nhóm theo b.Username:

```sql
SELECT b.Username AS [User],
       SUM(d.UnitPrice * bd.Quantity * (1 - bd.Discount)) AS Revenue,
       COUNT(DISTINCT bd.BillId) AS Quantity,
       MIN(b.Checkin) AS FirstTime,
       MAX(b.Checkin) AS LastTime
FROM BillDetails bd
  JOIN Bills b ON b.Id = bd.BillId
WHERE b.Status = 1
  AND b.Checkout BETWEEN ? AND ?
GROUP BY b.Username
ORDER BY Revenue DESC;
```

*(Ở đây Quantity đếm số phiếu, có thể đếm số món tùy định nghĩa; FirstTime/LastTime lấy thời gian mở phiếu đầu tiên/cuối cùng trong khoảng.)*

**3. Giao diện RevenueManagerJDialog:**

Phần trên:
- Cho phép chọn khoảng thời gian. Có thể có 2 JDateChooser (hoặc sử dụng JTextField + định dạng) txtBegin, txtEnd. Kèm nút "Lọc" (btnFilter)
- Hoặc đơn giản hơn: cung cấp một combo cboTimeRanges với các giá trị: "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm này", "Tùy chọn...". Nếu chọn "Tùy chọn..." thì enable hai date picker cho phép chọn ngày cụ thể; nếu chọn cái khác thì tự động điền begin/end tương ứng (vd "Hôm nay" -> begin=đầu ngày, end=cuối ngày hiện tại)

Phần trung tâm:
- Một JTabbedPane với 2 tab:
  - **Tab 1**: "Theo Loại đồ uống" chứa bảng tblByCategory với cột: Loại, Doanh thu, Số lượng bán, Giá min, Giá max, Giá trung bình
  - **Tab 2**: "Theo Nhân viên" chứa bảng tblByUser với cột: Nhân viên, Doanh thu, Số phiếu, Giờ đầu tiên, Giờ cuối cùng

**4. Xử lý khi mở form:**
- Mặc định chọn "Hôm nay" (hoặc khoảng 7 ngày gần nhất). Thiết lập sẵn txtBegin = đầu ngày, txtEnd = cuối ngày hiện tại
- Gọi fillRevenue() cho tab đang chọn (mặc định tab 1) để hiển thị doanh thu ban đầu
- fillRevenue() sẽ kiểm tra tab hiện tại:
  - Nếu tab "Loại": gọi RevenueDAO.getByCategory(begin, end), lấy list và đổ vào tblByCategory
  - Nếu tab "Nhân viên": gọi RevenueDAO.getByUser(begin, end), lấy list đổ vào tblByUser
- Cài đặt việc khi chuyển tab (ChangeListener trên tabbedPane) cũng gọi lại fillRevenue() để cập nhật dữ liệu tương ứng tab mới

**5. Sự kiện chọn khoảng thời gian nhanh:**
- Nếu có cboTimeRanges: khi cboTimeRanges thay đổi, trong controller sẽ xử lý:

```java
switch(cboTimeRanges.getSelectedIndex()) {
    case 0: range = TimeRange.today(); break;
    case 1: range = TimeRange.thisWeek(); break;
    // ... 
}
txtBegin.setText(XDate.toString(range.getBegin()));
txtEnd.setText(XDate.toString(range.getEnd()));
fillRevenue();
```

- TimeRange có thể là một lớp tiện ích chứa cặp Date begin/end cho các khoảng thông dụng. Không có sẵn thì ta tự tính bằng Calendar
**6. Nút Lọc:** Nếu người dùng chọn ngày tùy ý rồi bấm Lọc, thì parse ngày từ txtBegin, txtEnd, kiểm tra hợp lệ (begin <= end) rồi gọi fillRevenue()

**7. Hiển thị kết quả:**
- Các số liệu doanh thu nên được định dạng tiền tệ (VD: 1,250,000 đ). Có thể dùng String.format() hoặc NumberFormat cho cột doanh thu
- Ngày giờ hiển thị định dạng dd/MM/yyyy HH:mm hoặc tùy ý

**8. In/ Xuất báo cáo:**
- (Tùy chọn nếu muốn) Thêm nút "Xuất Excel" hoặc "In báo cáo" cho phép xuất dữ liệu bảng ra file hoặc máy in, nhưng phần này không bắt buộc trong nhiều bài

*Chức năng Thống kê doanh thu giúp hoàn thiện bức tranh quản lý, cho thấy hiệu quả kinh doanh.*