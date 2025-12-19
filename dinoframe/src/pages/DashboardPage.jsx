import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Progress } from '@/components/ui/progress';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Wallet, TrendingUp } from 'lucide-react';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { getAccountSummary, getTransactions } from '@/lib/api';
import Sidebar from '@/components/Sidebar';
import TransferModal from '@/components/TransferModal';
import { cn } from '@/lib/utils'; // Assuming cn is used

// Level definitions
// Level definitions
const levels = [
    { name: 'Dino Egg 🥚', min: 0 },
    { name: 'Velociraptor 🦖', min: 10000 },
    { name: 'Triceratops 🦕', min: 50000 },
    { name: 'T-Rex King 👑', min: 250000 },
];

export default function DashboardPage() {
    const [user, setUser] = useState(null);
    const [account, setAccount] = useState(null);
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isTransferOpen, setIsTransferOpen] = useState(false);
    const navigate = useNavigate();

    // Calculate level based on balance
    const currentPoints = account ? Math.floor(account.balance) : 0;
    const currentLevel = levels.slice().reverse().find(l => currentPoints >= l.min) || levels[0];
    const nextLevel = levels.find(l => l.min > currentPoints);
    const progress = nextLevel
        ? ((currentPoints - currentLevel.min) / (nextLevel.min - currentLevel.min)) * 100
        : 100;

    // Mock chart data for now (or could be real from history)
    const chartData = [
        { name: 'Pzt', amount: 4000 },
        { name: 'Sal', amount: 3000 },
        { name: 'Çar', amount: 2000 },
        { name: 'Per', amount: 2780 },
        { name: 'Cum', amount: 1890 },
        { name: 'Cmt', amount: 2390 },
        { name: 'Paz', amount: 3490 },
    ];

    const fetchData = async () => {
        // ... (existing logic) ...
        const userStr = localStorage.getItem('dinoUser');
        if (!userStr) return;
        const userInfo = JSON.parse(userStr);

        try {
            const accData = await getAccountSummary(userInfo.id);
            setAccount(accData);

            if (accData && accData.id) {
                const txData = await getTransactions(accData.id);
                // Safe date parser helper
                const parseDate = (d) => {
                    if (!d) return new Date();
                    if (Array.isArray(d)) {
                        // [year, month, day, hour, minute, second] - Month is 0-indexed in JS Date!
                        // But Spring sends 1-indexed month. So d[1]-1.
                        return new Date(d[0], d[1] - 1, d[2], d[3], d[4], d[5] || 0);
                    }
                    return new Date(d);
                };

                const sortedTx = txData.sort((a, b) => parseDate(b.transactionDate) - parseDate(a.transactionDate));
                // Add parsed objects back to state so we don't re-parse in render
                setTransactions(sortedTx.map(t => ({ ...t, parsedDate: parseDate(t.transactionDate) })));
            }
        } catch (error) {
            console.error("Dashboard data fetch error:", error);
        }
    };

    useEffect(() => {
        const userStr = localStorage.getItem('dinoUser');
        if (!userStr) {
            navigate('/login');
            return;
        }

        const userInfo = JSON.parse(userStr);
        setUser(userInfo);

        const initFetch = async () => {
            await fetchData();
            setLoading(false);
        }
        initFetch();
    }, [navigate]);

    if (loading) return <div className="flex items-center justify-center h-screen text-white bg-background">
        <div className="flex flex-col items-center">
            <span className="text-4xl animate-bounce">🦖</span>
            <p className="mt-4 text-slate-400">Veriler Yükleniyor...</p>
        </div>
    </div>;

    return (
        <div className="flex min-h-screen bg-background text-foreground">
            {/* Sidebar (Desktop) */}
            <div className="hidden md:block w-72 fixed h-full border-r border-white/5 bg-black/20 backdrop-blur-xl">
                <Sidebar />
            </div>

            {/* Main Content */}
            <div className="flex-1 md:ml-72 p-8 overflow-y-auto">
                <header className="flex justify-between items-center mb-10">
                    <div>
                        <h1 className="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-white to-slate-400 bg-clip-text text-transparent">Panel</h1>
                        <p className="text-slate-400 mt-1">Tekrar hoşgeldin, {user?.ad}!</p>
                    </div>
                    <div className="flex items-center gap-4">
                        <Button
                            className="bg-primary text-black font-bold hover:bg-primary/90 shadow-lg shadow-primary/20"
                            onClick={() => setIsTransferOpen(true)}
                        >
                            <Wallet className="mr-2 h-4 w-4" /> Yeni Transfer
                        </Button>
                    </div>
                </header>

                <TransferModal
                    isOpen={isTransferOpen}
                    onClose={() => setIsTransferOpen(false)}
                    onSuccess={() => {
                        // Refresh data after transfer
                        fetchData();
                    }}
                    userAccountId={account?.accountNumber}
                />

                <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-7">

                    {/* Balance Card */}
                    <Card className="col-span-4 bg-gradient-to-br from-primary/20 to-black/40 border-primary/20 backdrop-blur-sm relative overflow-hidden">
                        <div className="absolute right-[-20px] bottom-[-20px] text-[150px] opacity-5 rotate-[-10deg]">🦖</div>
                        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                            <CardTitle className="text-sm font-medium text-primary-foreground/80">Toplam Bakiye</CardTitle>
                            <Wallet className="h-5 w-5 text-primary" />
                        </CardHeader>
                        <CardContent>
                            <div className="text-5xl font-black text-white tracking-tight">
                                ₺ {account?.balance ? account.balance.toLocaleString('tr-TR', { minimumFractionDigits: 2 }) : '0,00'}
                            </div>
                            <p className="text-sm text-primary/80 mt-2 font-medium flex items-center">
                                <span className="opacity-70 text-xs mr-2">{account?.accountNumber}</span>
                            </p>
                        </CardContent>
                    </Card>

                    {/* Level Card */}
                    <Card className="col-span-3 bg-black/40 border-white/10 backdrop-blur-md">
                        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                            <CardTitle className="text-sm font-medium text-slate-300">Sadakat Durumu</CardTitle>
                            <div className="h-8 w-8 rounded-full bg-primary/20 flex items-center justify-center text-primary">
                                <TrendingUp className="h-4 w-4" />
                            </div>
                        </CardHeader>
                        <CardContent>
                            <div className="flex justify-between items-end mb-4">
                                <div>
                                    <div className="text-3xl font-bold text-white">{currentLevel.name}</div>
                                    <p className="text-sm text-slate-400 font-medium">{currentPoints} Puan</p>
                                </div>
                                {nextLevel && (
                                    <div className="text-right">
                                        <div className="text-sm font-medium text-slate-300">Sonraki: {nextLevel.name}</div>
                                        <div className="text-xs text-slate-500">{nextLevel.min - currentPoints} puan kaldı</div>
                                    </div>
                                )}
                            </div>
                            <Progress value={progress} className="h-3 bg-white/5" indicatorClassName="bg-gradient-to-r from-primary to-emerald-400" />
                        </CardContent>
                    </Card>

                    {/* Chart Section */}
                    <Card className="col-span-4 bg-black/20 border-white/5">
                        <CardHeader>
                            <CardTitle className="text-slate-200">Haftalık Özet</CardTitle>
                        </CardHeader>
                        <CardContent className="pl-2">
                            <div className="h-[250px] w-full">
                                <ResponsiveContainer width="100%" height="100%">
                                    <AreaChart data={chartData}>
                                        <defs>
                                            <linearGradient id="colorAmount" x1="0" y1="0" x2="0" y2="1">
                                                <stop offset="5%" stopColor="hsl(var(--primary))" stopOpacity={0.3} />
                                                <stop offset="95%" stopColor="hsl(var(--primary))" stopOpacity={0} />
                                            </linearGradient>
                                        </defs>
                                        <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="rgba(255,255,255,0.05)" />
                                        <XAxis
                                            dataKey="name"
                                            stroke="#64748b"
                                            fontSize={12}
                                            tickLine={false}
                                            axisLine={false}
                                        />
                                        <YAxis
                                            stroke="#64748b"
                                            fontSize={12}
                                            tickLine={false}
                                            axisLine={false}
                                            tickFormatter={(value) => `₺${value}`}
                                        />
                                        <Tooltip
                                            contentStyle={{ backgroundColor: '#0f172a', borderRadius: '12px', border: '1px solid rgba(255,255,255,0.1)', color: '#fff' }}
                                            itemStyle={{ color: '#fff' }}
                                        />
                                        <Area
                                            type="monotone"
                                            dataKey="amount"
                                            stroke="hsl(var(--primary))"
                                            strokeWidth={3}
                                            fillOpacity={1}
                                            fill="url(#colorAmount)"
                                        />
                                    </AreaChart>
                                </ResponsiveContainer>
                            </div>
                        </CardContent>
                    </Card>

                    {/* Recent Transactions */}
                    <Card className="col-span-3 bg-black/20 border-white/5">
                        <CardHeader>
                            <CardTitle className="text-slate-200">Son İşlemler</CardTitle>
                            <CardDescription className="text-slate-500">Bu hafta {transactions.length} işlem yaptınız.</CardDescription>
                        </CardHeader>
                        <CardContent>
                            <Table>
                                <TableHeader>
                                    <TableRow className="border-white/5 hover:bg-transparent">
                                        <TableHead className="text-slate-400">İşlem</TableHead>
                                        <TableHead className="text-right text-slate-400">Tutar</TableHead>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    {transactions.length === 0 ? (
                                        <TableRow>
                                            <TableCell colSpan={2} className="text-center text-slate-500 py-4">Henüz işlem yok 🦖</TableCell>
                                        </TableRow>
                                    ) : (
                                        transactions.slice(0, 5).map((tx) => {
                                            const isIncome = tx.amount > 0; // Simple logic: positive amount is income? Or based on transactionType
                                            // The backend sends positive amounts for deposit, negative for withdrawal/transfer? 
                                            // Let's assume backend logic: if type is DEPOSIT it's +, else -
                                            // Or simplified: if amount > 0 display +, if < 0 display -

                                            // Actually backend sends absolute amount and type. 
                                            // Let's check Transaction.java: amount is BigDecimal. transactionType is ENUM (DEPOSIT, WITHDRAWAL, TRANSFER).
                                            // If DEPOSIT -> Green. If WITHDRAWAL/TRANSFER -> Red.

                                            const isPositive = tx.transactionType === 'DEPOSIT' || tx.transactionType === 'INCOMING_TRANSFER';
                                            // Note: Backend might just use TRANSFER and we need to check source/target.
                                            // For now let's rely on amount sign or type if available. 

                                            return (
                                                <TableRow key={tx.id} className="border-white/5 hover:bg-white/5 transition-colors">
                                                    <TableCell>
                                                        <div className="font-medium text-slate-200">{tx.description || tx.transactionType}</div>
                                                        <div className="text-xs text-slate-500">{tx.parsedDate ? tx.parsedDate.toLocaleDateString('tr-TR') : '-'}</div>
                                                    </TableCell>
                                                    <TableCell className={cn("text-right font-bold", isPositive ? 'text-emerald-400' : 'text-slate-300')}>
                                                        {isPositive ? '+' : '-'}₺{Math.abs(tx.amount).toLocaleString('tr-TR', { minimumFractionDigits: 2 })}
                                                    </TableCell>
                                                </TableRow>
                                            )
                                        })
                                    )}
                                </TableBody>
                            </Table>
                        </CardContent>
                    </Card>

                </div>
            </div>
        </div>
    );
}
