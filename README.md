# Blockchain Platform (Mini)

🚀 A lightweight blockchain trading backend built with Spring Boot.  
Supports user registration, Ethereum wallet creation, and real-time ETH balance query via Etherscan API.

---

## ✅ Features

- 🧑‍💼 User registration with email & password
- 🔐 Auto-generated ETH wallet address
- 💰 Real-time ETH balance query (`/api/balance`)
- 🧪 In-memory H2 database with `/h2-console` access
- 📡 Integration with [Etherscan API](https://etherscan.io/apis)

---

## 🛠️ How to Run

```bash
mvn spring-boot:run
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"email": "test@example.com", "password": "123456"}'
curl "http://localhost:8080/api/balance?address=0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045"
private final String apiKey = "YOUR_ETHERSCAN_API_KEY";

---

### 💾 保存并退出：

- `nano`: `Ctrl+O` → `Enter`，然后 `Ctrl+X`
- `vim`: `:wq`

---

## 🚀 第二步：提交并推送到 GitHub

```bash
git add README.md
git commit -m "📝 添加项目说明文档 README.md"
git push origin main
