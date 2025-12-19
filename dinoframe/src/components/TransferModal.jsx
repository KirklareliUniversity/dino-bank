import { useState } from 'react';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { transfer } from '@/lib/api';
import { Loader2 } from 'lucide-react';

export default function TransferModal({ isOpen, onClose, onSuccess, userAccountId }) {
    const [targetAccount, setTargetAccount] = useState('');
    const [amount, setAmount] = useState('');
    const [description, setDescription] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleTransfer = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        if (Number(amount) <= 0) {
            setError("Tutar 0'dan büyük olmalı.");
            setLoading(false);
            return;
        }

        try {
            await transfer({
                fromAccountNumber: userAccountId,
                toAccountNumber: targetAccount,
                amount: Number(amount),
                description: description || 'Transfer'
            });
            onSuccess();
            onClose();
            setTargetAccount('');
            setAmount('');
            setDescription('');
        } catch (err) {
            setError(err.message || 'Transfer başarısız.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px] bg-slate-900 text-white border-slate-700">
                <DialogHeader>
                    <DialogTitle>Para Transferi 💸</DialogTitle>
                    <DialogDescription className="text-slate-400">
                        Başka bir DinoBank hesabına anında para gönderin.
                    </DialogDescription>
                </DialogHeader>
                <form onSubmit={handleTransfer} className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="target" className="text-right text-slate-300">
                            Alıcı Hesap
                        </Label>
                        <Input
                            id="target"
                            placeholder="TR..."
                            className="col-span-3 bg-slate-800 border-slate-700 text-white focus:ring-primary"
                            value={targetAccount}
                            onChange={(e) => setTargetAccount(e.target.value)}
                            required
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="amount" className="text-right text-slate-300">
                            Tutar
                        </Label>
                        <Input
                            id="amount"
                            type="number"
                            placeholder="0.00"
                            className="col-span-3 bg-slate-800 border-slate-700 text-white focus:ring-primary"
                            value={amount}
                            onChange={(e) => setAmount(e.target.value)}
                            required
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="desc" className="text-right text-slate-300">
                            Açıklama
                        </Label>
                        <Input
                            id="desc"
                            placeholder="Örn: Kira"
                            className="col-span-3 bg-slate-800 border-slate-700 text-white focus:ring-primary"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </div>

                    {error && (
                        <div className="text-red-400 text-sm text-center bg-red-900/20 p-2 rounded">
                            {error}
                        </div>
                    )}

                    <DialogFooter>
                        <Button type="submit" disabled={loading} className="w-full bg-primary hover:bg-primary/90 text-black font-bold">
                            {loading ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : 'Gönder'}
                        </Button>
                    </DialogFooter>
                </form>
            </DialogContent>
        </Dialog>
    );
}
