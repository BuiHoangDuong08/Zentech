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

