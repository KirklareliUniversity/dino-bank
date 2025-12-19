import { useState, useEffect } from 'react';
import Sidebar from '@/components/Sidebar';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { getAccountSummary, getTransactions } from '@/lib/api';
import { cn } from '@/lib/utils';

export default function HistoryPage() {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            const userStr = localStorage.getItem('dinoUser');
            if (userStr) {
                const user = JSON.parse(userStr);
                try {
                    const accData = await getAccountSummary(user.id);
                    if (accData && accData.id) {
                        const txData = await getTransactions(accData.id);

                        const parseDate = (d) => {
                            if (!d) return new Date();
                            if (Array.isArray(d)) {
                                return new Date(d[0], d[1] - 1, d[2], d[3], d[4], d[5] || 0);
                            }
                            return new Date(d);
                        };

                        const sortedTx = txData.sort((a, b) => parseDate(b.transactionDate) - parseDate(a.transactionDate));
                        setTransactions(sortedTx.map(t => ({ ...t, parsedDate: parseDate(t.transactionDate) })));
                    }
                } catch (e) {
                    console.error("History fetch error:", e);
                } finally {
                    setLoading(false);
                }
            }
        };
        fetchData();
    }, []);

    return (
        <div className="flex min-h-screen bg-background text-foreground">
            <div className="hidden md:block w-72 fixed h-full border-r border-white/5 bg-black/20 backdrop-blur-xl">
                <Sidebar />
            </div>

            <div className="flex-1 md:ml-72 p-8 overflow-y-auto">
                <header className="mb-10">
                    <h1 className="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-orange-400 to-red-600 bg-clip-text text-transparent">Geçmiş İşlemler</h1>
                    <p className="text-slate-400 mt-1">Hesap hareketlerinizin detaylı dökümü.</p>
                </header>

                <Card className="bg-black/40 border-white/10">
                    <CardHeader>
                        <CardTitle className="text-white">İşlem Geçmişi</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <Table>
                            <TableHeader>
                                <TableRow className="border-white/5 hover:bg-transparent">
                                    <TableHead className="text-slate-400">Tarih</TableHead>
                                    <TableHead className="text-slate-400">İşlem Tipi</TableHead>
                                    <TableHead className="text-slate-400">Açıklama</TableHead>
                                    <TableHead className="text-right text-slate-400">Tutar</TableHead>
                                    <TableHead className="text-right text-slate-400">Durum</TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {loading ? (
                                    <TableRow>
                                        <TableCell colSpan={5} className="text-center py-8">Yükleniyor...</TableCell>
                                    </TableRow>
                                ) : transactions.length === 0 ? (
                                    <TableRow>
                                        <TableCell colSpan={5} className="text-center py-8 text-slate-500">İşlem bulunamadı.</TableCell>
                                    </TableRow>
                                ) : (
                                    transactions.map((tx) => {
                                        const isPositive = tx.transactionType === 'DEPOSIT' || tx.transactionType === 'INCOMING_TRANSFER';
                                        return (
                                            <TableRow key={tx.id} className="border-white/5 hover:bg-white/5">
                                                <TableCell className="text-slate-300">
                                                    {tx.parsedDate ? tx.parsedDate.toLocaleDateString('tr-TR') + ' ' + tx.parsedDate.toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' }) : '-'}
                                                </TableCell>
                                                <TableCell className="text-slate-300">{tx.transactionType}</TableCell>
                                                <TableCell className="text-slate-300">{tx.description}</TableCell>
                                                <TableCell className={cn("text-right font-bold", isPositive ? 'text-emerald-400' : 'text-slate-300')}>
                                                    {isPositive ? '+' : '-'}₺{Math.abs(tx.amount).toLocaleString('tr-TR', { minimumFractionDigits: 2 })}
                                                </TableCell>
                                                <TableCell className="text-right">
                                                    <span className="px-2 py-1 rounded bg-green-900/40 text-green-400 text-xs font-bold">
                                                        {tx.status}
                                                    </span>
                                                </TableCell>
                                            </TableRow>
                                        );
                                    })
                                )}
                            </TableBody>
                        </Table>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
